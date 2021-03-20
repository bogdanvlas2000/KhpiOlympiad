package khpi.khpi_olympiad.repository.profile;

import khpi.khpi_olympiad.model.profile.University;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRepository extends CrudRepository<University, Integer> {
    public University findByEngName(String engName);

    @Query("SELECT u FROM University u WHERE" +
            " u.engName LIKE :word OR u.ukrName LIKE :word OR u.ukrShortName LIKE :word")
    public List<University> search(@Param("word") String word);
}
