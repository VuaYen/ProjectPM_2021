package miu.edu.product.web;


import miu.edu.product.domain.*;
import miu.edu.product.dto.Cart;
import miu.edu.product.dto.CheckOutModel;
import miu.edu.product.dto.RemoveCartModel;
import miu.edu.product.exception.OrderCreateException;
import miu.edu.product.repository.VendorRepository;
import miu.edu.product.service.OrderService;
import miu.edu.product.service.ProductService;
import miu.edu.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@SessionAttributes("myCart")
public class BuyerController {

    private final ServletContext context;
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    private VendorRepository vendorService;


    @Value("0.08")
    private Double taxRate;

    @Value("15")
    private Double shippingFee;

    @Autowired
    public BuyerController(ServletContext context,
                           ProductService productService,
                           OrderService orderService,
                           UserService userService) {
        this.context = context;
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;

    }

    @ModelAttribute("myCart")
    public Cart getMyCart() {
        return new Cart();
    }

    @GetMapping("/404")
    public String pTest() {
        return "buyer/404";
    }

    @GetMapping("/check-out/{vendor}")
    public String showCheckout(@PathVariable(name = "vendor") String vendor,HttpServletRequest request, Model model) throws OrderCreateException {

        Cart cart = (Cart) model.asMap().get("myCart");
        OnlineOrder order = new OnlineOrder();

        order.setTax(11.92);
        order.setTotal(175.92);
        order.setShippingFee(15.0);
        Vendor vendor1= vendorService.findVendorByUserName(vendor);
        order.setVendor(vendor1);

        CheckOutModel checkOutModel = new CheckOutModel();
        User customer = new User();
        Address address = new Address();
        address.setCity("Fairfield");
        address.setState("IA");
        address.setStreet("1000 4th");
        address.setZip("52557");

        customer.setAddress(address);
        customer.setEmail("yen637@gmail.com");
        customer.setUserName("thepham");

        checkOutModel.setCustomer(customer);
        checkOutModel.setOrder(order);

        orderService.placeOrder(order);
        System.out.println(order.getOrderno());

        model.addAttribute("model", checkOutModel);
        return "buyer/checkout";
    }

    @PostMapping("/check-out")
    public String proceedCheckout(@ModelAttribute("checkOutModel") CheckOutModel checkOutModel, Model model, HttpServletRequest request) throws OrderCreateException {
        Cart cart = (Cart) model.asMap().get("myCart");
        OnlineOrder order = checkOutModel.getOrder();
        if (checkOutModel.getOrder().getShippingAddress() != null && !checkOutModel.getOrder().getShippingAddress().trim().equals("")) {
            order.setShippingAddress(checkOutModel.getOrder().getShippingAddress());
        }
        orderService.placeOrder(order);
        System.out.println("order.getOrderno()");

        Integer orderItemTotal = 0;
        for (OrderDetail detail : order.getOrderDetailList()) {
            orderItemTotal += detail.getQty();
        }
        cart.setTotal(cart.getTotal() - orderItemTotal);

        return "buyer/home";
    }

    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest httpServletRequest) {
        return "/buyer/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("user", new User());
        return "/buyer/registration";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/buyer/registration";
        }


        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/shopping-cart")
    public String showCart(Model model) {
        return "/buyer/shopping-cart";
    }

    @GetMapping("/add-to-cart/{productId}/{qty}")
    public @ResponseBody
    Integer addToCart(@PathVariable(name = "productId") Integer productId, @PathVariable(name = "qty") Integer quantity, Model model, @ModelAttribute(name = "myCart") Cart cart) {
        HashMap<String, OnlineOrder> orders = cart.getOrderList();

        OnlineOrder order = null;
        Product product = null;
        OrderDetail orderDetail = null;
        List<OrderDetail> detailList = null;

        Boolean alreadyInCart = false;

        for (Map.Entry item : orders.entrySet()) {
            if (alreadyInCart) {
                break;
            }
            order = (OnlineOrder) item.getValue();
            for (OrderDetail detail : order.getOrderDetailList()) {
                Product temp = detail.getProduct();
                if (productId == temp.getProductnumber()) {
                    product = temp;
                    orderDetail = detail;
                    alreadyInCart = true;
                    break;
                }
            }
        }

        if (!alreadyInCart) {
            Optional<Product> opt = productService.getById(productId);
            if (opt.isPresent()) {
                product = opt.get();
                String sellerId = product.getVendor().getUserName();
                orderDetail = new OrderDetail();

                if (cart.getOrderList().containsKey(sellerId)) {
                    order = cart.getOrderList().get(sellerId);
                    detailList = order.getOrderDetailList();
                } else {
                    order = new OnlineOrder();
                    order.setStatus(OrderStatus.NEW);
                    order.setTax(0.00);
                    order.setShippingFee(shippingFee);
                    order.setShippingAddress("order.getCustomer().getAddress()");
                    order.setTotal(shippingFee);
                    detailList = new ArrayList<>();
                    order.setOrderDetailList(detailList);
                }

                orderDetail = new OrderDetail();
                orderDetail.setProduct(product);
                orderDetail.setQty(0);
                orderDetail.setSellPrice(product.getPrice());

                detailList.add(orderDetail);
            } else {
                return -1;
            }
        }

        Double newTax = order.getTax() + quantity * product.getPrice() * taxRate;
        Double newTotal = order.getTotal() + (quantity * product.getPrice()) + (quantity * product.getPrice() * taxRate);
        order.setTax(newTax);
        order.setTotal(newTotal);

        Integer newQty = orderDetail.getQty() + quantity;
        orderDetail.setQty(newQty);

        if (!alreadyInCart) {
            cart.getOrderList().put(product.getVendor().getUserName(), order);
        }

        cart.setTotal(cart.getTotal() + quantity);
        return cart.getTotal();
    }

    @GetMapping("/remove-from-cart/{productId}")
    public @ResponseBody
    RemoveCartModel removeFromCart(@PathVariable(name = "productId") Integer productId, Model model) {
        Cart cart = (Cart) model.asMap().get("myCart");

        HashMap<String, OnlineOrder> orders = cart.getOrderList();
        OnlineOrder order = null;
        OrderDetail orderDetail = null;
        List<OrderDetail> detailList = null;
        Product product = null;

        Boolean found = false;
        for (Map.Entry item : orders.entrySet()) {
            if (found) {
                break;
            }

            order = (OnlineOrder) item.getValue();
            for (OrderDetail detail : order.getOrderDetailList()) {
                Product temp = detail.getProduct();
                if (temp.getProductnumber() == productId) {
                    product = temp;
                    orderDetail = detail;
                    detailList = order.getOrderDetailList();
                    detailList.remove(detail);
                    Double newTotal = order.getTotal() - ((detail.getQty() * detail.getSellPrice()) + (detail.getQty() * detail.getSellPrice() * taxRate));
                    Double newTax = order.getTax() - (detail.getQty() * detail.getSellPrice() * taxRate);
                    order.setTotal(newTotal);
                    order.setTax(newTax);

                    found = true;
                    break;
                }
            }
        }

        Integer cartItemTotal = cart.getTotal() - orderDetail.getQty();
        Integer orderItemTotal = 0;

        for (OrderDetail detail : order.getOrderDetailList()) {
            orderItemTotal += detail.getQty();
        }

        if (orderItemTotal == 0) {
            orders.remove(product.getVendor().getUserName());
        }

        cart.setTotal(cartItemTotal);
        RemoveCartModel removeCartModel = new RemoveCartModel(cartItemTotal, orderItemTotal, product.getProductnumber(), order);
        return removeCartModel;
    }

    @GetMapping("/cart-adjust-qty/{productId}/{qty}")
    public @ResponseBody
    RemoveCartModel adjustQuantity(@PathVariable("productId") Integer productId, @PathVariable("qty") Integer qty, Model model) {
        Cart cart = (Cart) model.asMap().get("myCart");

        HashMap<String, OnlineOrder> orders = cart.getOrderList();
        OnlineOrder order = null;
        OrderDetail orderDetail = null;
        List<OrderDetail> detailList = null;
        Product product = null;

        Integer oldQty = 0;

        Boolean found = false;
        for (Map.Entry item : orders.entrySet()) {
            if (found) {
                break;
            }

            order = (OnlineOrder) item.getValue();
            for (OrderDetail detail : order.getOrderDetailList()) {
                Product temp = detail.getProduct();
                if (temp.getProductnumber() == productId) {
                    product = temp;
                    orderDetail = detail;
                    oldQty = orderDetail.getQty();
                    Double newTax = order.getTax() + (qty * product.getPrice() * taxRate) - (oldQty * product.getPrice() * taxRate);
                    Double newTotal = order.getTotal() + (qty * product.getPrice() + (qty * product.getPrice() * taxRate))
                            - (oldQty * product.getPrice() + (oldQty * product.getPrice() * taxRate));
                    order.setTotal(newTotal);
                    order.setTax(newTax);
                    orderDetail.setQty(qty);

                    found = true;
                    break;
                }
            }
        }

        Integer cartItemTotal = cart.getTotal() - oldQty + qty;
        cart.setTotal(cartItemTotal);

        RemoveCartModel removeCartModel = new RemoveCartModel(cartItemTotal, 0, product.getProductnumber(), order);
        return removeCartModel;
    }

}
