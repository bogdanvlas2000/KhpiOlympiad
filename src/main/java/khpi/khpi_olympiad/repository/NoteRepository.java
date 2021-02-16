package khpi.khpi_olympiad.repository;

import java.util.List;

import khpi.khpi_olympiad.model.Note;
import khpi.khpi_olympiad.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteRepository extends CrudRepository<Note, Integer> {
//    public List<Note> findByLabelContainingOrMessageContaining(String word1, String word2);

    public List<Note> findByUserId(Integer id);

    @Query(
            "select n from Note n " +
                    "where n.user.id = :user_id and " +
                    "(n.label like :word or n.message like :word)")
    public List<Note> search(@Param("user_id") int userId, @Param("word") String word);
}
