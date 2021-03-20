package khpi.khpi_olympiad.repository.auth;

import khpi.khpi_olympiad.model.auth.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    public Optional<Role> findByName(String name);
}
