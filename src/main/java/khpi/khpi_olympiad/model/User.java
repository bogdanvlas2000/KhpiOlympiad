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

    private boolean ready;
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @ManyToMany(mappedBy = "subscribedUsers", fetch = FetchType.EAGER)
    private Set<Event> events;

//    public User(String username, String password, String email, String name, int age, String gender) {
//        this.profile = new Profile()
//        this.name = name;
//        this.age = age;
//        this.gender = gender;
//        this.enabled = true;
//    }

    public void subscribe(Event event) {
        event.addUser(this);
        this.events.add(event);
    }

    public void setProfile(Profile profile) {
        profile.setUser(this);
        this.profile = profile;
    }

    public boolean checkReady() {
        this.ready = true;
        if (profile == null) {
            ready = false;
        }
//        if (age < 1) {
//            ready = false;
//        }
//        if (gender == null || (!gender.equals("male") && !gender.equals("female"))) {
//            ready = false;
//        }
        return this.ready;
    }
}
