package miu.edu.product.web;

import miu.edu.product.domain.Product;
import miu.edu.product.domain.User;
import miu.edu.product.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private IUserService userService;


    @GetMapping("/user")
    public String showUserList(Model model, HttpServletRequest request){

        List<User> userList = userService.all();
        List<String> roles = Arrays.asList("ADMIN", "USER");

        model.addAttribute("users", userList);
        model.addAttribute("roleList", roles);
        return "admin/users";
    }

}
