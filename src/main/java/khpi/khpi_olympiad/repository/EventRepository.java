package khpi.khpi_olympiad.repository;

import khpi.khpi_olympiad.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
    public Event findByTitle(String title);

    public Event findByDescription(String description);

    public List<Event> findByTitleContainingOrDescriptionContaining(String word1, String word2);


    @Query(
            "select e from Event e WHERE e.title like :word or e.description like  :word")
    public List<Event> search(@Param("word") String word);

    @Query(
            "select e from Event e " +
                    "where e.createdByUser.id = :user_id and " +
                    "(e.title like :word or e.description like :word)")
    public List<Event> searchByUserCreated(@Param("user_id") int userId, @Param("word") String word);

}
