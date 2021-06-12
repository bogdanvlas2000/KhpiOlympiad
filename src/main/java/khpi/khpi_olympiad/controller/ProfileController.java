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
import java.util.Map;

@RestController
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

    @GetMapping("/match_password")
    public boolean matchPassword(String password, Principal prl) {
        var user = userRepository.findByUsername(prl.getName());
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        return matches;
    }

    @PostMapping("/change_password")
    public void changePassword(@RequestBody Map<String, String> body, Principal prl) {
        var user = userRepository.findByUsername(prl.getName());

        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }
}
