package khpi.khpi_olympiad.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String password;
    private boolean enabled;

    private String email;
    private String name;
    private int age;
    private String gender;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @ManyToMany(mappedBy = "subscribedUsers")
    private Set<Event> events;

    public User(String username, String password, String email, String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.enabled = true;
    }

    public void subscribe(Event event) {
        event.addUser(this);
        this.events.add(event);
    }
}
