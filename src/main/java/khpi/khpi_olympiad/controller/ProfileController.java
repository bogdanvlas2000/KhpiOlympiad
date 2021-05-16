package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.profile.Profile;
import khpi.khpi_olympiad.repository.auth.UserRepository;
import khpi.khpi_olympiad.repository.profile.CityRepository;
import khpi.khpi_olympiad.repository.profile.ProfileRepository;
import khpi.khpi_olympiad.repository.profile.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private UserRepository userRepository;

    private ProfileRepository profileRepository;

    private PasswordEncoder passwordEncoder;

    private UniversityRepository universityRepository;

    private CityRepository cityRepository;

    @Autowired
    public ProfileController(UserRepository userRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder, UniversityRepository universityRepository, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.universityRepository = universityRepository;
        this.cityRepository = cityRepository;
    }





    @GetMapping("/psw_change")
    public String changePassword(Model model, @ModelAttribute("error") String error) {
        if (!model.containsAttribute("error")) {
            model.addAttribute("error", error);
        }
        return "/profile/change_password";
    }

    @PostMapping("/psw_change")
    public String resetPassword(Principal prl,
                                @RequestParam("oldPassword") String oldPassword,
                                @RequestParam("newPassword") String newPassword,
                                RedirectAttributes attr) {
        var user = userRepository.findByUsername(prl.getName());
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return "redirect:/profile/psw_changed";
        } else {
            attr.addFlashAttribute("error", "You should enter your actual password!");
            return "redirect:/profile/psw_change";
        }
    }

    @GetMapping("psw_changed")
    public String passwordChanged(Model model) {
        model.addAttribute("message", "Your password has been changed!");
        model.addAttribute("go_profile", true);
        return "message";
    }
}
