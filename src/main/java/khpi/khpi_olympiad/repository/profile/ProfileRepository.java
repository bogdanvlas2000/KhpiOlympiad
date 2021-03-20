package khpi.khpi_olympiad.repository.profile;

import khpi.khpi_olympiad.model.profile.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {
}
