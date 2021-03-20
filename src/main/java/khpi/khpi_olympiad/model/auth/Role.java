package khpi.khpi_olympiad.model.auth;

import java.util.List;

import javax.persistence.*;

import khpi.khpi_olympiad.model.auth.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<User> users;

    @Override
    public String toString() {
        return name;
    }

    public Role(String name) {
        super();
        this.name = name;
    }
}
