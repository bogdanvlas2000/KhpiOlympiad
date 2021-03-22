package khpi.khpi_olympiad.model.profile;

import khpi.khpi_olympiad.model.auth.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int age;
    private String gender;

    @ManyToOne(fetch = FetchType.EAGER)
    private University university;

    @Lob
    private byte[] image;

    @OneToOne(mappedBy = "profile")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    public boolean isComplete() {
        boolean complete = true;
        if (name == null || name.isEmpty() || age < 1 ||
                gender == null || (!gender.equals("male") && !gender.equals("female"))
                || university == null
        ) {
            complete = false;
        }
        return complete;
    }
}
