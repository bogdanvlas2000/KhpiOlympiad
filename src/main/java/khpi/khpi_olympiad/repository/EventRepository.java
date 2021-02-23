package khpi.khpi_olympiad.repository;

import khpi.khpi_olympiad.model.Event;
import khpi.khpi_olympiad.model.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
    public Event findByName(String name);

    public Event findByDescription(String description);

    public List<Event> findByNameContainingOrDescriptionContaining(String word1, String word2);

    @Query(
            "select e from Event e " +
                    "where e.createdByUser.id = :user_id and " +
                    "(e.name like :word or e.description like :word)")
    public List<Event> search(@Param("user_id") int userId, @Param("word") String word);

}
