package miu.edu.product.web;

import miu.edu.product.domain.User;
import miu.edu.product.service.IUserService;
import miu.edu.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@SessionAttributes("myCart")
public class AuthController {

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

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
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model,
                               HttpServletRequest request) {

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            ObjectError error = new ObjectError("password", "Password should match");
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/buyer/registration";
        }


        User u = userService.save(user);
        authenticateUserAndSetSession(u, request);

        return "redirect:/";
    }


    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        String username = user.getUserName();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
