//package miu.edu.product.web;
//
//
//import miu.edu.product.domain.Payment;
//import miu.edu.product.domain.Visa;
//import miu.edu.product.service.PaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@RestController
//@RequestMapping("/payment")
//public class PaymentController {
//    @Autowired
//    PaymentService paymentService;
//
////    @Autowired
////    ProductOrderService productOrderService;
////
////    @Autowired
////    ProductService productService;
////
////    @Autowired
////    UserService userService;
////
////
////    @Autowired
////    Address billingAddressService;
//
//
//
//
//
//    @GetMapping(value = {"/", ""})
//    public String paymentInput(@ModelAttribute("payment") Payment payment) {
//
//        return "/user/paymentForm";
//    }
//
//    @PostMapping(value = {"/", ""})
//    public String savePayment(@Valid @ModelAttribute("payment") Payment payment, BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
//        if (result.hasErrors()) {
//            return "/user/paymentForm";
//        } else {
//
//            return "redirect:/buyer/payment_input/payment-success";
//        }
//
//    }
//    @GetMapping(value = {"/payment-success"})
//    public String PaymentSuccess(Model model) {
//        model.addAttribute("allPaymentMethod", paymentService.getAllPayment());
//        return "/user/orderCompleted";
//    }
//    @GetMapping(value = {"/paymentList"})
//    public String PaymentUpdate(Model model) {
//        model.addAttribute("allPaymentMethod", paymentService.getAllPayment());
//        return "/user/paymentSuccess";
//    }
//
//    @GetMapping("/editPayment/{id}")
//    public String showUpdateForm(@PathVariable("id") long id, Model model) {
//        Payment payment = paymentService.findById(id);
//        model.addAttribute("paymentUpdate", payment);
//
//        return "/user/update_Payment";
//    }
//
//    @PostMapping(value = {"/editPayment/update/{id}"})
//    public String updatePayment(@PathVariable("id") long id, Payment payment, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            payment.setId(id);
//            return "/user/update_Payment";
//        }
//        paymentService.save(payment);
//        model.addAttribute("allPaymentMethod", paymentService.getAllPayment());
//        return "/user/paymentSuccess";
//    }
//
//    @GetMapping("/deletePayment/{id}")
//    public String deleteUser(@PathVariable("id") long id, Payment payment, Model model) {
//        payment = paymentService.findById(id);
//        paymentService.delete(payment);
//        model.addAttribute("allPaymentMethod", paymentService.getAllPayment());
//        return "/user/paymentSuccess";
//    }
//
//
//    //Check validity
//    public boolean checkCardValidity(Visa payment){
//        boolean cardNum;
//        Calendar calendar = Calendar.getInstance();
//        String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + "(?<mastercard>5[1-5][0-9]{14}))";
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(payment.getCardNumber());
//
//        if(matcher.matches()){
//            if(payment.getExperienceDate().before(calendar.getTime())){
//                return matcher.matches();
//            }
//        }
//        return matcher.matches();
//
//    }
//
//
//}
