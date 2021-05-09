package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.repository.auth.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class MainController {
    private UserRepository userRepository;

    @GetMapping
    public String homePage(Model model, Principal user) {
        if (user != null) {
            model.addAttribute("message", "Welcome, " + user.getName());
        } else {
            model.addAttribute("message", "Welcome to my app!");
        }
        return "home";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/users";
    }
}
