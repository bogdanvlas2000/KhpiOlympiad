package khpi.khpi_olympiad.model;

import khpi.khpi_olympiad.model.auth.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private Set<User> subscribedUsers;

    public void addUser(User user) {
        subscribedUsers.add(user);
    }

    public void removeUser(User user) {
        subscribedUsers.remove(user);
    }
}
