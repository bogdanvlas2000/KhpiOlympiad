package khpi.khpi_olympiad.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    private int id;

    private String username;
    private String password;
    private boolean enabled;

    private String email;
    private String name;
    private int age;
    private String gender;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Note> notes;

    public User(String username, String password, String email, String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.enabled = true;
        this.notes = new ArrayList<>();
    }

    public void saveNote(Note note) {
        note.setUser(this);
        this.notes.add(note);
    }
}
