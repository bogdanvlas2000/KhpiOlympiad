package khpi.khpi_olympiad.repository.profile;

import khpi.khpi_olympiad.model.profile.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {
    public City findByUkrName(String ukrName);

    @Query("SELECT c FROM City c WHERE c.ukrName LIKE :word")
    public List<City> search(@Param("word") String word);
}
