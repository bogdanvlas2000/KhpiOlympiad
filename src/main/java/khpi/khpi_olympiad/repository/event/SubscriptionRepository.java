package khpi.khpi_olympiad.repository.event;

import khpi.khpi_olympiad.model.auth.User;
import khpi.khpi_olympiad.model.event.Event;
import khpi.khpi_olympiad.model.event.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    List<Subscription> findByUserId(int userId);

    List<Subscription> findByEventId(int eventId);

    Subscription findByUserIdAndEventId(int userId, int eventId);
}
