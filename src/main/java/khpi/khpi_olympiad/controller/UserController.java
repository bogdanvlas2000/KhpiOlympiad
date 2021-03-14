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


    @GetMapping("/profile")
    public String profile(Model model, Principal prl) {
        var user = userRepository.findByUsername(prl.getName());
        model.addAttribute("profile", user.getProfile());
        model.addAttribute("user", user);
        boolean isReady = user.checkReady();
        if (isReady) {
            model.addAttribute("ready", true);
        } else {
            model.addAttribute("not_ready", true);
        }
        return "/users/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, Principal prl) {
        var user = userRepository.findByUsername(prl.getName());
        Profile profile = user.getProfile() != null ? user.getProfile() : new Profile();
        model.addAttribute("profile", profile);
        model.addAttribute("username", user.getUsername());

        return "users/edit_profile";
    }

    @PostMapping("/profile/edit")
    public String changeProfile(@ModelAttribute("profile") Profile profile, Principal prl) {
        var user = userRepository.findByUsername(prl.getName());
        user.setProfile(profile);
        profileRepository.save(profile);
        userRepository.save(user);
        return "redirect:/users/profile";
    }

    // admin methods

    @GetMapping("/all")
    public String usersList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/users";
    }


}
