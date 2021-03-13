package khpi.khpi_olympiad.service;

import khpi.khpi_olympiad.model.User;
import khpi.khpi_olympiad.model.UserSecurity;
import khpi.khpi_olympiad.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


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
