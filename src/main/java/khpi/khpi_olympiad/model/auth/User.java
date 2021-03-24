package khpi.khpi_olympiad.model.auth;

import java.util.Set;

import javax.persistence.*;

import khpi.khpi_olympiad.model.Event;
import khpi.khpi_olympiad.model.profile.Profile;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String email;

    private String password;

    private boolean ready;
    private boolean enabled;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @ManyToMany(mappedBy = "subscribedUsers", fetch = FetchType.EAGER)
    private Set<Event> events;

    public void subscribe(Event event) {
        event.addUser(this);
        this.events.add(event);
    }

    public void unsubscribe(Event event) {
        event.removeUser(this);
        this.events.remove(event);
    }

    public void setProfile(Profile profile) {
        profile.setUser(this);
        this.profile = profile;
    }

    public boolean checkReady() {
        if (this.profile == null) {
            return false;
        } else {
            this.ready = profile.isComplete();
            return this.ready;
        }
    }
}
