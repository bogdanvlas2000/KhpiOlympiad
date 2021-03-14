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

    @OneToOne(mappedBy = "profile")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
