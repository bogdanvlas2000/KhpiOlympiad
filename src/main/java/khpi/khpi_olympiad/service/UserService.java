package khpi.khpi_olympiad.service;

import khpi.khpi_olympiad.exception.UsernameAlreadyExistsException;
import khpi.khpi_olympiad.model.Event;
import khpi.khpi_olympiad.model.profile.Profile;
import khpi.khpi_olympiad.model.auth.User;
import khpi.khpi_olympiad.repository.profile.ProfileRepository;
import khpi.khpi_olympiad.repository.auth.RoleRepository;
import khpi.khpi_olympiad.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ProfileRepository profileRepository;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
        var role = roleRepository.findById(1);
        Set<Event> events = new HashSet<>();
        Profile profile = profileRepository.save(new Profile());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        user.setRole(role.get());
        user.setProfile(profile);
        user.setEvents(events);

        return userRepository.save(user);
    }
}
