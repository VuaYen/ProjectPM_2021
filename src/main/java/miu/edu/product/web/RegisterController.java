package miu.edu.product.web;

import miu.edu.product.domain.User;
import miu.edu.product.dto.Cart;
import miu.edu.product.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@SessionAttributes({"accBuyer", "accVendor"})
public class RegisterController {

    @Autowired
    IUserService userService;


    @GetMapping("/reg")
    public  String doRegistration(){
        return "buyer/registration";
    }

    @GetMapping("/logout")
    public String showLogOut(HttpServletRequest httpServletRequest) {
        return "/buyer/login";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute(value="user") User user,
                              HttpServletRequest request){

//        if(user==null)
//            user= new User();
//        //classify account types
//        String userType = request.getParameter("user-type");
//        UserType accType = UserType.valueOf(userType);
//        boolean res;
//
//        if(accType == UserType.Vendor){
//            User accVendor = new User();
//            accVendor.setUserName(user.getUserName());
//            accVendor.setPassword(user.getPassword());
//            accVendor.setFirstName(user.getFirstName());
//            accVendor.setLastName(user.getLastName());
//            accVendor.setStatus(false);
//            accVendor.setEmail(user.getEmail());
//            accVendor.setUserType(UserType.Vendor);
//            res = userService.createUser(accVendor);
//        }
//        else{
//            if(accType == UserType.Buyer){
//                User accBuyer = new User();
//                accBuyer.setUserName(user.getUserName());
//                accBuyer.setPassword(user.getPassword());
//                accBuyer.setFirstName(user.getFirstName());
//                accBuyer.setLastName(user.getLastName());
//                accBuyer.setStatus(false);
//                accBuyer.setEmail(user.getEmail());
//                accBuyer.setUserType(UserType.Buyer);
//
//
//                Address address = new Address();
//                address.setCity("Temp city");
//                address.setState("Temp state");
//                address.setStreet("Temp street");
//                address.setZip("00000");
//
//                accBuyer.setAddress(address);
//                res = userService.createUser(accBuyer);
//            }
//            else{
//                //admin type, do nothing
//                User accAdmin = new User();
//                accAdmin.setUserName(user.getUserName());
//                accAdmin.setPassword(user.getPassword());
//                accAdmin.setFirstName(user.getFirstName());
//                accAdmin.setLastName(user.getLastName());
//                accAdmin.setStatus(true);
//                accAdmin.setEmail(user.getEmail());
//                accAdmin.setUserType(UserType.Admin);
//                res = userService.createUser(accAdmin);
//            }
//        }



        return "redirect:/";
    }

    @GetMapping("/listReg")
    public String getListRegistration(Model model){
        //Get list of Seller accounts from DB

        ArrayList<User> listSellerAcc = new ArrayList<>();

        model.addAttribute("listSellerAcc", listSellerAcc);
        return "/admin/listreg";
    }

    @PostMapping("approveAcc")
    public String approveAccount(@RequestParam(value = "uname") String userName){
        //update the status Approved to the user name
        updateUserStatus(userName, true);//true: approved
        return "/admin/listreg";
    }

    @PostMapping("rejectAcc")
    public String rejectAccount(@RequestParam(value = "uname") String userName){
        //update the status Approved to the user name
        updateUserStatus(userName, false);//false: reject
        return "/admin/listreg";
    }

    private void updateUserStatus(String userName, boolean isApprove) {
        //call service to update the status
    }



    @ModelAttribute("myCart")
    public Cart getMyCart() {
        return new Cart();
    }
}
