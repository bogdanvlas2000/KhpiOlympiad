package khpi.khpi_olympiad.model.event;

import khpi.khpi_olympiad.model.auth.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @EqualsAndHashCode.Exclude
    private LocalDateTime subscriptionDate;


    public Subscription(User user, Event event, LocalDateTime subscriptionDate) {
        this.user = user;
        this.event = event;
        this.subscriptionDate = subscriptionDate;
    }
}
