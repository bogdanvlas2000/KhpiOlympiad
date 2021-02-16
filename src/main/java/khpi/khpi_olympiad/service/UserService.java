package khpi.khpi_olympiad.service;

import khpi.khpi_olympiad.model.Note;
import khpi.khpi_olympiad.model.Role;
import khpi.khpi_olympiad.model.User;
import khpi.khpi_olympiad.repository.RoleRepository;
import khpi.khpi_olympiad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExists(user.getUsername());
        }
        var role = roleRepository.findById(1);
        List<Note> notes = new ArrayList<Note>();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRole(role.get());
        user.setNotes(notes);

        return userRepository.save(user);
    }
}
