package khpi.khpi_olympiad.model.event;

import khpi.khpi_olympiad.model.auth.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime eventDate;


    @OneToMany(mappedBy = "event")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Subscription> subscriptions = new LinkedHashSet<>();

    public boolean addSubscription(Subscription subscription) {
        return subscriptions.add(subscription);
    }

    public boolean removeSubscription(Subscription subscription) {
        return subscriptions.remove(subscription);
    }
}
