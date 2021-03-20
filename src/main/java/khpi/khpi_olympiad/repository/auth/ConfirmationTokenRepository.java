package khpi.khpi_olympiad.repository.auth;

import khpi.khpi_olympiad.model.auth.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Integer> {
    ConfirmationToken findByTokenString(String tokenString);
}
