package miu.edu.product.service;

import miu.edu.product.domain.OnlineOrder;
import miu.edu.product.domain.OrderStatus;
import miu.edu.product.exception.OrderCreateException;
import miu.edu.product.repository.OnlineOrderRepository;
import miu.edu.product.repository.ProductRepository;
import miu.edu.product.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class OrderServiceImp implements OrderService {

    private final OnlineOrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;

    @Autowired
    public OrderServiceImp(OnlineOrderRepository orderRepository,
                           ProductRepository productRepository,VendorRepository vendorRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.vendorRepository = vendorRepository;
    }

    public void placeOrder(OnlineOrder order) throws OrderCreateException {
        try {

            OnlineOrder realOrder = new OnlineOrder();
            realOrder =order;
//            realOrder.setTax(order.getTax());
//            realOrder.setTotal(order.getTotal());
//            realOrder.setShippingFee(order.getShippingFee());
//            realOrder.setShippingAddress(order.getShippingAddress());
            realOrder.setStatus(OrderStatus.NEW);
            Date currentDate = new Date();
            realOrder.setDateCreate(currentDate);
            realOrder.setDateShipping(getShippingDate(currentDate));
            realOrder.setOrderno("ORD_#" +currentDate.getTime()+ "123");
            System.out.println(realOrder.getOrderno());

//            List<Integer> idList = new ArrayList<>();
//            for (OrderDetail detail : order.getOrderDetailList()) {
//                idList.add(detail.getProduct().getProductnumber());
//            }
//
//            Iterable<Product> products = productRepository.findAllById(idList);
//            List<Product> productList = StreamSupport.stream(products.spliterator(), false)
//                    .collect(Collectors.toList());
//
//            String shippingAddress = order.getShippingAddress();
//            realOrder.setShippingAddress(shippingAddress);

//            List<OrderDetail> detailList = new ArrayList<>();
//            for (OrderDetail detail : order.getOrderDetailList()) {
//                OrderDetail realDetail = new OrderDetail();
//                realDetail.setQty(detail.getQty());
//                realDetail.setSellPrice(detail.getSellPrice());
//                realDetail.setTotal(detail.getSellPrice() * detail.getQty());
//                Product product = productList.stream().filter(p -> p.getProductnumber() == detail.getProduct().getProductnumber()).findFirst().get();
//                realDetail.setProduct(product);
//                detailList.add(realDetail);
//            }
//
//            realOrder.setOrderDetailList(detailList);
            orderRepository.save(realOrder);
            realOrder.notify(realOrder);

        } catch (Exception e) {
            throw new OrderCreateException("Could not create exception");
        }
    }

    public List<OnlineOrder> getAll() {
        return toList(orderRepository.findAll()) ;
    }

    public OnlineOrder getOrder(Integer id) {

        return orderRepository.findById(id).get();
    }

    public OnlineOrder getOrder(String orderNo) {
        return null;
    }

    public OnlineOrder getOrderWithDetails(String orderNo) {
        return null;
    }

    public OnlineOrder getOrderWithDetails(Integer id) {
        return null;
    }


    @Override

    public void updateStatus(OrderStatus status, Integer id) {

        if (status == OrderStatus.DELIVERED) orderRepository.updateDateDelivered(new Date(), id);
        else orderRepository.updateDateDelivered(null, id);

        orderRepository.updateStatus(status, id);

    }


    private Date getShippingDate(Date dateCreate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(dateCreate); // Now use today date.
        c.add(Calendar.DATE, 14); // Adding 5 days
        return c.getTime();
    }
    public static <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
