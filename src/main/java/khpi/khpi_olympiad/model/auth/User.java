package khpi.khpi_olympiad.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import khpi.khpi_olympiad.model.event.Event;
import khpi.khpi_olympiad.model.profile.Profile;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @JsonIgnore
    private String password;
    private boolean ready;
    @JsonIgnore
    private boolean enabled;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Role role;

    @ManyToMany
    @JoinTable(name = "subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    public void subscribe(Event event) {
        events.add(event);
        event.getSubscribers().add(this);
    }

    public void unsubscribe(Event event) {
        events.remove(event);
        event.getSubscribers().remove(this);
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
