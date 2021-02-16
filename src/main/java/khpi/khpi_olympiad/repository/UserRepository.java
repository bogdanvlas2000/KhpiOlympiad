package khpi.khpi_olympiad.repository;

import khpi.khpi_olympiad.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByUsername(String username);

    public User findByEmail(String email);
}
