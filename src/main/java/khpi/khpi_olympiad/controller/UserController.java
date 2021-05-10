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

    @GetMapping("/{id}")
    public String getProfilePage(Model model, @PathVariable Integer id) {
        var user = userRepository.findById(id).get();
        if (user.getProfile().getImage() != null) {
            var image = Base64.getMimeEncoder().encodeToString(user.getProfile().getImage());
            model.addAttribute("image", image);
        }

        model.addAttribute("user", user);
        model.addAttribute("profile", user.getProfile());


        boolean isReady = user.checkReady();
        if (isReady) {
            var university = user.getProfile().getUniversity();
            var city = university.getCity().getUkrName();


            model.addAttribute("university", university.getUkrName());
            model.addAttribute("city", city);
            model.addAttribute("ready", true);
        } else {
            model.addAttribute("not_ready", true);
        }
        return "/main/resources/templates/pages/profile.html";
    }
}
