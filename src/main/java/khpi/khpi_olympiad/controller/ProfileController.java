package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.Profile;
import khpi.khpi_olympiad.repository.ProfileRepository;
import khpi.khpi_olympiad.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private UserRepository userRepository;

    private ProfileRepository profileRepository;

    public ProfileController(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @GetMapping
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
        return "/profile/profile";
    }

    @GetMapping("/edit")
    public String editProfile(Model model, Principal prl) {
        var user = userRepository.findByUsername(prl.getName());
        Profile profile = user.getProfile() != null ? user.getProfile() : new Profile();
        model.addAttribute("profile", profile);
        model.addAttribute("username", user.getUsername());

        return "/profile/edit";
    }

    @PostMapping("/edit")
    public String changeProfile(@ModelAttribute("profile") Profile profile, Principal prl) {
        var user = userRepository.findByUsername(prl.getName());
        user.setProfile(profile);
        profileRepository.save(profile);
        userRepository.save(user);
        return "redirect:/profile";
    }
}
