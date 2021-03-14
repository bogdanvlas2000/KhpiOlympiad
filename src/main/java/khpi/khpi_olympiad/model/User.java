package khpi.khpi_olympiad.model;

import java.util.Set;

import javax.persistence.*;

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

    private String name;
    private int age;
    private String gender;


    private boolean ready;
    private boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @ManyToMany(mappedBy = "subscribedUsers", fetch = FetchType.EAGER)
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

    public boolean checkReady() {
        this.ready = true;
        if (name == null || name.isEmpty()) {
            ready = false;
        }
        if (age < 1) {
            ready = false;
        }
        if (gender == null || (!gender.equals("male") && !gender.equals("female"))) {
            ready = false;
        }

        return this.ready;
    }
}
