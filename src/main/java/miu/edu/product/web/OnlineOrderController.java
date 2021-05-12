package miu.edu.product.web;

import miu.edu.product.domain.OnlineOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("/seller/orders")
public class OnlineOrderController {

    @RequestMapping()
    public String getOrders(Model model, HttpSession session){
        OnlineOrder order= new OnlineOrder();

        model.addAttribute("order",order);
        return "admin/orderForm";
    }
    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id")Long id, Model model){

        return "admin/orderForm";
    }
    @GetMapping(value = "/edit")
    public String orderForm(Model model){

        return "admin/orderForm";
    }
}
