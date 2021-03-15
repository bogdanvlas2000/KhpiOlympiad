package khpi.khpi_olympiad.repository;

import khpi.khpi_olympiad.model.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Integer> {
    ConfirmationToken findByTokenString(String tokenString);
}
