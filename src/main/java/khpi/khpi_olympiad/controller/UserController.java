package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.auth.User;
import khpi.khpi_olympiad.repository.profile.ProfileRepository;
import khpi.khpi_olympiad.repository.auth.UserRepository;
import khpi.khpi_olympiad.service.UserSecurityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

}
