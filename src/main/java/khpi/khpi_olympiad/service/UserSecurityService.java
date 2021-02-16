package khpi.khpi_olympiad.service;

import khpi.khpi_olympiad.model.Note;
import khpi.khpi_olympiad.model.User;
import khpi.khpi_olympiad.model.UserSecurity;
import khpi.khpi_olympiad.repository.RoleRepository;
import khpi.khpi_olympiad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserSecurityService implements UserDetailsService {

    private UserRepository userRepository;


    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user with username: " + username);
        }
        UserDetails principal = new UserSecurity(user);
        return principal;
    }
}
