package miu.edu.product.web;

import miu.edu.product.domain.Address;
import miu.edu.product.domain.User;
import miu.edu.product.service.IUserService;
import miu.edu.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@SessionAttributes("myCart")
public class ProfileController {

    @Autowired
    private IUserService userService;

    @GetMapping("/profile")
    public String showProfile(HttpServletRequest request, Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getByUsername(username);
        if (user != null) {
            if (user.getAddress() == null) {
                user.setAddress(new Address());
            }
        }

        model.addAttribute("reg", user);
        model.addAttribute("user", user);
        return "buyer/profile";
    }

    @PostMapping("/saveProfile")
    public String registerProfile(@ModelAttribute("user") User user,
                                  BindingResult bindingResult,
                                  Model model,
                                  HttpServletRequest request) {

        if (user != null) {
            userService.saveProfile(user);
        }
        return "redirect:/profile";
    }
}
