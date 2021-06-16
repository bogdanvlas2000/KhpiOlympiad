package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.repository.auth.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;


@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;

}
