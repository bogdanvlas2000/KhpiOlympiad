package khpi.khpi_olympiad.model;

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

    @Lob
    private byte[] image;

    @OneToOne(mappedBy = "profile")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    public boolean isComplete() {
        boolean complete = true;
        if (name == null || name.isEmpty() || age < 1 ||
                gender == null || (!gender.equals("male") && !gender.equals("female"))) {
            complete = false;
        }
        return complete;
    }
}
