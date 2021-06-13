package khpi.khpi_olympiad.model.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String firstName;
    private String patronymicName;
    private String secondName;
    private int age;
    private String gender;
    private int courseNumber;
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    private University university;

    @Lob
    private byte[] image;

    @OneToOne(mappedBy = "profile")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
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
