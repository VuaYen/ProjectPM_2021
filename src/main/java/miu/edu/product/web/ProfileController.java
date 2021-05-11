package miu.edu.product.web;

import miu.edu.product.domain.Address;
import miu.edu.product.domain.User;
import miu.edu.product.service.IUserService;
import miu.edu.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private IUserService userService;

    @GetMapping("/profile")
    public String showProfile(HttpServletRequest request, Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getByUsername(username);
        if(user != null){
            if(user.getAddress() == null){
                user.setAddress(new Address());
            }
        }

        model.addAttribute("reg", user);
        model.addAttribute("accBuyer", user);
        return "buyer/profile";
    }
}
