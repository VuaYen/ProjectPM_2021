package miu.edu.product.service.impl;

import miu.edu.product.domain.*;
import miu.edu.product.repository.*;
import miu.edu.product.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ScheduledDeliveryRepository scheduledDeliveryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private CommissionRepository commissionRepository;

    public OrderResponse checkoutOrder(OrderInput orderInput) throws Exception {
        Cart cart = cartRepository.findBySessionId(orderInput.getSessionId()).orElse(null);

        if (cart == null) {
            throw new Exception("Cart Not Found");
        }

        User user = cart.getUser();

        // verify the cart
        Set<CartItem> cartItems = cart.getCartItems();

        // verify cart items
        for (CartItem item : cartItems) {
            verifyCartItem(item);
        }

        //verify cards
        for (ChargeModel charge : orderInput.getCharges()) {
            int cardId = charge.getCardId();
            Card card = cardRepository.findById(cardId).orElse(null);
            verifyCard(card, cart);
        }

        Address billingAddress = orderInput.getBillingAddress();
//
//        // verify delivery address
        Address shippingAddress = orderInput.getShippingAddress();
           // if users address doesnt match ? so

        // charge the cards
        List<Transaction> transactions = new LinkedList<>();
        for (ChargeModel charge : orderInput.getCharges()) {
            Transaction transaction = chargeCard(charge.getCardId(), charge.getAmount());
            transactions.add(transaction);
        }

        //validate if the total amount paid is equal to the required order total
        OnlineOrder order = new OnlineOrder();

        order.setDeliveryAddress(shippingAddress);
        order.setTransactions(transactions);
        order.setCustomer(user);
        order.setStatus(OrderStatus.RECEIVED);
        order.setTax(getTax(cart));

        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQty(cartItem.getQuantity());
            orderDetail.setSellPrice(cartItem.getRate());
            orderDetailList.add(orderDetail);
        }
        order.setOrderDetailList(orderDetailList);
        order.setDateCreate(new Timestamp(System.currentTimeMillis()));

        orderRepository.save(order);

        //create a delivery address
        DeliveryAddress address = getDeliveryAddress(orderInput, shippingAddress, user);
        scheduleDeliver(address, order);

        // Delete user cart after checkout
        cartRepository.delete(cart);

        return new OrderResponse(order);
    }

    public ScheduledDeliveryResponse updateOrderStatus(int deliveryId, int status) throws Exception {
        ScheduledDelivery delivery = scheduledDeliveryRepository.findById(deliveryId).orElse(null);

        if (delivery == null) {
            throw new Exception("Delivery not found");
        }
        OrderStatus status1 = OrderStatus.RECEIVED;
        if (status == 1) {
            status1 = OrderStatus.ON_THE_WAY;
        }

        if (status == 2) {
            status1 = OrderStatus.SHIPPED;
            //TODO
            // Has to send email to customer that order shipped
        }

        if (status == 3) {
            status1 = OrderStatus.DELIVERED;

            Commission commission = new Commission();
            double total = delivery.getDetails()
                    .stream()
                    .map(e -> e.getQty() * e.getSellPrice())
                    .reduce(0.00, Double::sum);

            commission.setCommission(total * commission.getCommissionRate());
            commission.setCommissionRate(commission.getCommissionRate());
            commission.setDelivery(delivery);
            commission.setEarnedDate(new Timestamp(System.currentTimeMillis()));
            commission.setSale(total);
            commission.setVendor(delivery.getVendor());

            commissionRepository.save(commission);

            delivery.setDeliveredDate(new Timestamp(System.currentTimeMillis()));

            //TODO
            // Has to send email to customer that order Completed

        }

        if (status == 4) {
            status1 = OrderStatus.CANCELLED;
            delivery.setDeliveredDate(new Timestamp(System.currentTimeMillis()));
            //TODO
            // Has to send email to customer that order cancelled
        }

        delivery.setStatus(status1);
        scheduledDeliveryRepository.save(delivery);

        return new ScheduledDeliveryResponse(delivery);
    }

    // API for admin module
    public PagedResponse<ScheduledDeliveryResponse> getActiveOrders(int pageNum, int itemsPerPage, boolean loadActive) throws Exception {
        if (pageNum < 1) {
            throw new Exception("Page number is invalid.");
        }
        Pageable paging = PageRequest.of(pageNum - 1, itemsPerPage, Sort.by(Sort.Direction.DESC, "id"));

        List<OrderStatus> statusList = new ArrayList<>();
        if (loadActive) {
            statusList.add(OrderStatus.RECEIVED);
            statusList.add(OrderStatus.ON_THE_WAY);
            statusList.add(OrderStatus.SHIPPED);
        } else {
            statusList.add(OrderStatus.DELIVERED);
            statusList.add(OrderStatus.CANCELLED);
        }

        Page<ScheduledDelivery> pagedResult = scheduledDeliveryRepository.findAllByStatusIn(statusList, paging);
        int totalPages = pagedResult.getTotalPages();

        List<ScheduledDeliveryResponse> products = pagedResult.toList().stream()
                .map(ScheduledDeliveryResponse::new)
                .collect(Collectors.toList());

        return new PagedResponse<>(totalPages, pageNum, itemsPerPage, products);
    }

    public PagedResponse<ScheduledDeliveryResponse> getUserOrders(int userId, int pageNum, int itemsPerPage, boolean loadActive) throws Exception {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new Exception("User is not found");
        }

        if (pageNum < 1) {
            throw new Exception("Page number is invalid.");
        }
        Pageable paging = PageRequest.of(pageNum - 1, itemsPerPage, Sort.by(Sort.Direction.DESC, "id"));

        List<OrderStatus> statusList = new ArrayList<>();
        if (loadActive) {
            statusList.add(OrderStatus.RECEIVED);
            statusList.add(OrderStatus.ON_THE_WAY);
            statusList.add(OrderStatus.SHIPPED);
        } else {
            statusList.add(OrderStatus.DELIVERED);
            statusList.add(OrderStatus.CANCELLED);
        }

        Page<ScheduledDelivery> pagedResult = scheduledDeliveryRepository.findAllByUserIdAndStatusIn(userId, statusList, paging);
        int totalPages = pagedResult.getTotalPages();
        List<ScheduledDeliveryResponse> products = pagedResult.toList().stream().map(ScheduledDeliveryResponse::new).collect(Collectors.toList());
        return new PagedResponse<>(totalPages, pageNum, itemsPerPage, products);
    }

    public PagedResponse<ScheduledDeliveryResponse> getVendorOrders(int vendorId, int pageNum, int itemsPerPage, boolean loadActive) throws Exception {

        Vendor vendor = vendorRepository.findById(vendorId).orElse(null);
        if (vendor == null) {
            throw new Exception("Vendor is not found");
        }

        if (pageNum < 1) {
            throw new Exception("Page number is invalid.");
        }

        Pageable paging = PageRequest.of(pageNum - 1, itemsPerPage, Sort.by(Sort.Direction.DESC, "id"));

        List<OrderStatus> statusList = new ArrayList<>();
        if (loadActive) {
            statusList.add(OrderStatus.RECEIVED);
            statusList.add(OrderStatus.ON_THE_WAY);
            statusList.add(OrderStatus.SHIPPED);
        } else {
            statusList.add(OrderStatus.DELIVERED);
            statusList.add(OrderStatus.CANCELLED);
        }

        Page<ScheduledDelivery> pagedResult = scheduledDeliveryRepository.findAllByVendorIdAndStatusIn(vendorId, statusList, paging);
        int totalPages = pagedResult.getTotalPages();
        List<ScheduledDeliveryResponse> products = pagedResult.toList().stream().map(ScheduledDeliveryResponse::new).collect(Collectors.toList());
        return new PagedResponse<>(totalPages, pageNum, itemsPerPage, products);
    }

    @Override
    public CartItemResponse deleteCartItem(int cartItemId, String sessionId) throws Exception {
        Cart cart = cartRepository.findBySessionId(sessionId).orElse(null);
        if (cart == null) {
            throw new Exception("Cart not found");
        }

        CartItem item = cart.getCartItems().stream().reduce(null, (a, b) -> b.getId() == cartItemId ? b : a);
        if (item == null) {
            throw new Exception("Cart item not found");
        }

        Set<CartItem> cartItems = cart.getCartItems();
        cartItems.remove(item);
        cartRepository.delete(cart);
        User user = cart.getUser();
        if (user == null) {
            return new CartItemResponse(item);
        }
        return new CartItemResponse(item, (int)user.getId());
    }

    private void scheduleDeliver(DeliveryAddress address, OnlineOrder order) {
        // group order items by vendor
        Map<Integer, List<OrderDetail>> map = new HashMap<>();
        Map<Integer, Vendor> vendorMap = new HashMap<>();
        for (OrderDetail item : order.getOrderDetailList()) {
            int vendorId = (int) item.getProduct().getVendor().getId();
            if (!map.containsKey(vendorId)) {
                map.put(vendorId, new LinkedList<>());
            }

            if (!vendorMap.containsKey(vendorId)) {
                vendorMap.put(vendorId, item.getProduct().getVendor());
            }

            map.get(vendorId).add(item);
        }

        for (int vendorId : map.keySet()) {
            ScheduledDelivery delivery = new ScheduledDelivery();
            delivery.setAddress(address);
            delivery.setDetails(map.get(vendorId));
            delivery.setVendor(vendorMap.get(vendorId));
            delivery.setDeliveryDate(new Timestamp(System.currentTimeMillis()));
            delivery.setStatus(OrderStatus.RECEIVED);
            delivery.setUser(order.getCustomer());
            scheduledDeliveryRepository.save(delivery);
            //send an email to the vendor here
            //TODO
            // has to send email to vendor that order scheduled
        }

        //TODO
        // has to send email to customer that order scheduled
    }

    private DeliveryAddress getDeliveryAddress(OrderInput input, Address address, User user) {
        DeliveryAddress address1 = new DeliveryAddress();

        if (input.getReceiverEmail() == null) {
            address1.setEmail(user.getEmail());
        } else {
            address1.setEmail(input.getReceiverEmail());
        }

        if (input.getReceiverName() == null) {
            address1.setReceiver(user.getUserName());
        } else {
            address1.setReceiver(input.getReceiverName());
        }

        address1.setPhone(input.getReceiverPhone());
        address1.setStreet(address.getStreet());
        address1.setCity(address.getCity());
        address1.setState(address.getState());
        address1.setZip(address.getZip());

//        return orderRepository.saveAddress(address1);
        return address1;
    }

    private void verifyCartItem(CartItem item) throws Exception {
        if (item == null) {
            throw new Exception("Cart item is empty.");
        }

        Product product = item.getProduct();
        if (product == null || product.getStatus() != ProductStatus.Active) {
            throw new Exception("Product not found");
        }

        Vendor vendor = product.getVendor();
        if (vendor == null || vendor.getStatus() != VendorStatus.APPROVED) {
            throw new Exception("Product vendor has not been approved yet.");
        }

        Category category = product.getCategory();
//        if (category == null || category.isDeleted()) {
//            throw new Exception("Category does not exist.");
//        }
    }

    private void verifyCard(Card card, Cart cart) throws Exception {
        if (card == null) {
            throw new Exception("Card not found");
        }

        if (card.getUser().getId() != cart.getUser().getId()) {
            throw new Exception("Card ending with " + card.getLast4() + " does not belong to you.");
        }
    }

    private Transaction chargeCard(int cardId, double amount) throws Exception {
        Card card = cardRepository.findById(cardId).orElse(null);
        if (card == null) {
            throw new Exception("Card not found");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int) amount * 100);
        params.put("currency", "usd");
        params.put("customer", card.getCustomerId());
        params.put("description", "Successfully charged");

        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setAmount(amount);

        transactionRepository.save(transaction);

        return transaction;
    }

    private double getTax(Cart cart) {
        double total = 0;
        for (CartItem item : cart.getCartItems()) {
            total += item.getQuantity() * item.getRate();
        }
        return (total * 0.07);
    }


}
