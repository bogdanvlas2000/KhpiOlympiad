package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.User;
import khpi.khpi_olympiad.repository.UserRepository;
import khpi.khpi_olympiad.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    private UserSecurityService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserSecurityService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/all")
    public String usersList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/users";
    }

    @GetMapping("/create")
    public String createUser() {
        return "/users/create_user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        /*
         * Registration process
         */
        return "redirect:/users/all";
    }

    @GetMapping("/info/{id}")
    public String userInfo(@PathVariable(name = "id") int id, Model model) {
        /*
         * Info about user
         */
        return "/users/user_info";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") int id) {
        /*
         * delete user
         */
        return "redirect:/users";
    }
}
