package khpi.khpi_olympiad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {
    @GetMapping
    public String home(Model model, Principal user) {
        if (user != null) {
            model.addAttribute("message", "Welcome, " + user.getName());
        } else {
            model.addAttribute("message", "Welcome to my app!");
        }
        return "home";
    }
}
