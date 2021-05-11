package miu.edu.product.web;


import miu.edu.product.domain.*;

import miu.edu.product.dto.CheckOutModel;
import miu.edu.product.exception.OrderCreateException;
import miu.edu.product.service.OrderService;
import miu.edu.product.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@SessionAttributes("myCart")
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    OrderService orderService;
    //    @Autowired
//    RestTemplate restTemplate;
    static private OrderDetail orderDetail;


    @GetMapping
    public String getPaymentForm(Model model) {
        model.addAttribute("card", new Card());
        return "/buyer/paymentForm";
    }
    @PostMapping
    public String setOrder(@ModelAttribute("order") CheckOutModel onlineOrder, BindingResult result, Model model){
        //double total = onlineOrder.getTotal();
        model.addAttribute("onlineOrder",onlineOrder);
        return "redirect:payment";
    }

    @PostMapping("/save")
    public String savePayment(@Valid @ModelAttribute("card") Card card, BindingResult result, Model model) {
        double cost;

        RestTemplate restTemplate1 = new RestTemplate();
        Calendar calendar = Calendar.getInstance();
        String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + "(?<mastercard>5[1-5][0-9]{14}))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(card.getCardNumber());
        System.out.println(calendar.getTime());
        if(!((Matcher) matcher).matches()){
            result.rejectValue("cardNumber", "error.card", "Enter Correct Card Number");
            System.out.println(("Here"));
            return "/buyer/paymentForm";
        }

//        OnlineOrder onlineOrder = restTemplate1.getForObject("http://order/{id}",OnlineOrder.class);
//        User user = onlineOrder.getCustomer();
//        orderService.getOrder(card.getCustomerId());
//        double cardAmount = card.getValue();
//        double payment = onlineOrder.getTotal();
//        card.setValue(cardAmount-payment);

        if (result.hasErrors()) {
            System.out.println("inside");
            model.addAttribute("card", card);
            return "/buyer/paymentForm";
        }
        paymentService.save(card);
        System.out.println("In payment Service");
        return "/buyer/paymentSuccess";
    }
    @GetMapping("/editPayment/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Card payment = paymentService.findById(id);
        model.addAttribute("paymentUpdate", payment);

        return "/buyer/update_Payment";
    }

    @PostMapping(value = {"/editPayment/update/{id}"})
    public String updatePayment(@PathVariable("id") int id, Card payment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            payment.setId(id);
            return "/buyer/update_Payment";
        }
        paymentService.save(payment);
        model.addAttribute("allPaymentMethod", paymentService.getAllPayment());
        return "/buyer/paymentSuccess";
    }

    @GetMapping("/deletePayment/{id}")
    public String deleteUser(@PathVariable("id") long id, Card payment, Model model) {
        payment = paymentService.findById(id);
        paymentService.delete(payment);
        model.addAttribute("allPaymentMethod", paymentService.getAllPayment());
        return "/buyer/paymentSuccess";
    }
    @PostMapping("/check-out")
    public String proceedCheckout(@ModelAttribute("orderDetail") OrderDetail checkOutModel, Model model, HttpServletRequest request) throws OrderCreateException {
        System.out.println("Checkout");
        model.addAttribute("card", new Card());
        PaymentController.orderDetail = checkOutModel;
        return "/buyer/paymentForm";
    }
}
