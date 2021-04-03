package khpi.khpi_olympiad.model.event;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import khpi.khpi_olympiad.model.auth.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@IdClass(UserEventKey.class)
@Data
@NoArgsConstructor
public class Subscription {
    @Id
    @Column(name = "user_id")
    private int userId;
    @Id
    @Column(name = "event_id")
    private int eventId;

    private LocalDateTime subscriptionDate;

    public Subscription(int userId, int eventId, LocalDateTime subscriptionDate) {
        this.userId = userId;
        this.eventId = eventId;
        this.subscriptionDate = subscriptionDate;
    }
}
