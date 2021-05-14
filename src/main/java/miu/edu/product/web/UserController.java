package miu.edu.product.web;

import miu.edu.product.domain.Product;
import miu.edu.product.domain.Role;
import miu.edu.product.domain.User;
import miu.edu.product.domain.UserForm;
import miu.edu.product.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String showUserList(Model model, HttpServletRequest request) {

        List<User> userList = userService.all();
        model.addAttribute("users", userList);
        return "admin/users";
    }

    @GetMapping("/user/save/{username}")
    public String showUserRole(@PathVariable String username, Model model, HttpServletRequest request) {

        User user = userService.getByUsername(username);

        List<Role> roles = new ArrayList<>();

        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        roles.add(role);

        role = new Role();
        role.setId(2L);
        role.setName("USER");
        roles.add(role);

        model.addAttribute("user", user);
        model.addAttribute("allRoles", roles);
        return "admin/userForm";
    }

    @PostMapping("/user/save/role")
    public String registerUserRole(@ModelAttribute("user") User user,
                                   BindingResult bindingResult,
                                   Model model,
                                   HttpServletRequest request) {

        System.out.println(user.getRoles().size() + " ***********");
        List<User> userList = userService.all();
        model.addAttribute("users", userList);

        return "redirect:/admin/user";
    }

}
