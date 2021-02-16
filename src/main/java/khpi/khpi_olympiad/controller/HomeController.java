package khpi.khpi_olympiad.controller;

import java.security.Principal;

import khpi.khpi_olympiad.model.User;
import khpi.khpi_olympiad.service.UserService;
import khpi.khpi_olympiad.service.UsernameAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    private UserService userService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public HomeController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public String home(Model model, Principal user) {
        if (user != null) {
            model.addAttribute("message", "Welcome, " + user.getName());
        } else {
            model.addAttribute("message", "Welcome to my app!");
        }
        return "home";
    }

    @GetMapping("/login")
    public String signIn() {
        return "login";
    }

    @GetMapping("/signup")
    public String signUp(Model model, @ModelAttribute("username_exists") String msg) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String registerNewUser(
            @ModelAttribute("user") User user,
            HttpServletRequest request,
            RedirectAttributes attributes) {
        String password = user.getPassword();
        try {
            user = userService.registerNewUser(user);
            System.out.println(user + " registered!");
            request.login(user.getUsername(), password);
        } catch (UsernameAlreadyExists e) {
            attributes.addFlashAttribute("username_exists", "Username alredy exists!");
            attributes.addFlashAttribute("user", user);
            return "redirect:/signup";
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
