package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.Profile;
import khpi.khpi_olympiad.model.User;
import khpi.khpi_olympiad.repository.ProfileRepository;
import khpi.khpi_olympiad.repository.UserRepository;
import khpi.khpi_olympiad.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    private ProfileRepository profileRepository;

    private UserSecurityService userService;

    @Autowired
    public UserController(UserRepository userRepository, ProfileRepository profileRepository, UserSecurityService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.profileRepository = profileRepository;
    }




    // admin methods

    @GetMapping("/all")
    public String usersList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/users";
    }


}
