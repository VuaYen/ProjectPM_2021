package miu.edu.product.web;

import miu.edu.product.domain.Product;
import miu.edu.product.domain.Role;
import miu.edu.product.domain.User;
import miu.edu.product.domain.UserForm;
import miu.edu.product.service.IRoleService;
import miu.edu.product.service.IUserService;
import miu.edu.product.utils.StringToRoleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/admin")
@SessionAttributes("myCart")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping("/user")
    public String showUserList(Model model, HttpServletRequest request) {

        List<User> userList = userService.all();
        model.addAttribute("users", userList);
        return "admin/users";
    }

    @GetMapping("/user/save/{username}")
    public String showUserRole(@PathVariable String username, Model model, HttpServletRequest request) {

        User user = userService.getByUsername(username);
        List<Role> roles = roleService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("allRoles", roles);
        return "admin/userForm";
    }

    @PostMapping("/user/save/role")
    public String registerUserRole(@ModelAttribute("user") User user,
                                   Model model,
                                   @RequestParam(value = "roles", required = false) String[] roles,
                                   HttpServletRequest request) {

        if (roles != null) {
            StringToRoleConverter converter = new StringToRoleConverter();
            Set<Role> roleSet = new HashSet<>();
            for (String r : roles) {
                System.out.println(r);
                Role role = converter.convert(r);
                if (role != null) {
                    roleSet.add(role);
                }
            }

            User u = userService.getByUsername(user.getUserName());
            u.setRoles(roleSet);
            userService.save(u);
        }

        List<User> userList = userService.all();
        model.addAttribute("users", userList);

        return "redirect:/admin/user";
    }

}
