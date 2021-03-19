package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.exception.UsernameAlreadyExistsException;
import khpi.khpi_olympiad.model.ConfirmationToken;
import khpi.khpi_olympiad.model.User;
import khpi.khpi_olympiad.repository.ConfirmationTokenRepository;
import khpi.khpi_olympiad.repository.UserRepository;
import khpi.khpi_olympiad.service.EmailSenderService;
import khpi.khpi_olympiad.service.PasswordGenerator;
import khpi.khpi_olympiad.service.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class AccountController {

    private UserRepository userRepository;

    private UserService userService;

    private ConfirmationTokenRepository confirmationTokenRepository;

    private EmailSenderService emailSenderService;

    private PasswordGenerator passwordGenerator;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(UserRepository userRepository, UserService userService, ConfirmationTokenRepository confirmationTokenRepository, EmailSenderService emailSenderService, PasswordGenerator passwordGenerator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSenderService = emailSenderService;
        this.passwordGenerator = passwordGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String signIn() {
        return "/auth/login";
    }

    @GetMapping("/signup")
    public String signUp(Model model, @ModelAttribute("username_exists") String msg) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "/auth/signup";
    }

    @PostMapping("/signup")
    public String registerNewUser(
            @ModelAttribute("user") User user,
            RedirectAttributes attributes) {

        String password = user.getPassword();
        try {
            user = userService.registerNewUser(user);
            System.out.println(user + " registered!");
            ///успех
            var confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            // SEND MESSSAGE with Confirm token
            var mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete registration!");
            mailMessage.setFrom("khpi_default@mail.com");
            mailMessage.setText("To confirm yout account click here: " +
                    "http://localhost:8080/confirm-account?token=" + confirmationToken.getTokenString());

            emailSenderService.sendEmail(mailMessage);

            attributes.addFlashAttribute("email", user.getEmail());
            return "redirect:/successful_registration";
        } catch (UsernameAlreadyExistsException e) {
            attributes.addFlashAttribute("username_exists", "Username alredy exists!");
            attributes.addFlashAttribute("user", user);
            return "redirect:/signup";
        }
    }


    @GetMapping("/successful_registration")
    public String succesfulRegistration(Model model, @ModelAttribute("email") String email) {
        var msg = "Verification link sent to your email " + email;
        model.addAttribute("message", msg);
        model.addAttribute("go_home", true);
        return "/message";
    }

    @GetMapping("/confirm-account")
    public String confirmUserAccount(Model model,
                                     @RequestParam("token") String tokenString,
                                     HttpServletRequest request) {
        var confirmationToken = confirmationTokenRepository.findByTokenString(tokenString);
        if (confirmationToken != null) {
            var email = confirmationToken.getUser().getEmail();
            var user = userRepository.findByEmailIgnoreCase(email);
            user.setEnabled(true);
            userRepository.save(user);
            confirmationTokenRepository.deleteById(confirmationToken.getId());
            model.addAttribute("message", "Congratulations! Your account has been activated!");
            model.addAttribute("go_login", true);
            return "/message";
        } else {
            model.addAttribute("error", "Link is invalid or account has been activated!");
            model.addAttribute("go_home", true);
            return "/message";
        }
    }

    @PostMapping("/psw_forgot")
    public String generatePasswordAndSendEmail(@RequestParam("username") String username, RedirectAttributes attr) {
        var user = userRepository.findByUsername(username);
        if (user != null) {
            var password = passwordGenerator.generatePassword(10);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            //SEND email with new password!!!!
            var mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Your new password");
            mailMessage.setFrom("khpi_default@mail.com");
            mailMessage.setText("This is your new password: " + password);
            emailSenderService.sendEmail(mailMessage);
            attr.addFlashAttribute("email", user.getEmail());
            return "redirect:/psw_sent";
        }
        return "redirect:/psw_sent";
    }

    @GetMapping("/psw_sent")
    public String newPasswordSent(Model model, @ModelAttribute("email") String email) {
        model.addAttribute("message", "New password sent to your email " + email);
        model.addAttribute("go_home", true);
        return "message";
    }
}
