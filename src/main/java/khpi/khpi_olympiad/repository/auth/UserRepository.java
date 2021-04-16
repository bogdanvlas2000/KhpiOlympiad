package khpi.khpi_olympiad.repository.auth;

import khpi.khpi_olympiad.model.auth.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByUsername(String username);

    public User findByEmailIgnoreCase(String email);


    @Query("SELECT u FROM User u WHERE " +
            "u.role.name = 'ROLE_USER' AND u.ready = TRUE ")
    public List<User> findReadyUsers();


    @Query("SELECT u FROM User u " +
            "WHERE u.role.name = 'ROLE_USER' AND (" +
            "u.username LIKE :word OR " +
            "u.email LIKE :word OR " +
            "u.profile.name LIKE :word OR " +
            "u.profile.university.ukrShortName LIKE :word OR " +
            "u.profile.university.ukrName LIKE :word OR " +
            "u.profile.university.engName LIKE :word OR " +
            "u.profile.university.city.ukrName LIKE :word)")
    public List<User> search(@Param("word") String word);
}
