package khpi.khpi_olympiad.repository.auth;

import khpi.khpi_olympiad.model.auth.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByUsername(String username);

    public User findByEmailIgnoreCase(String email);

}
