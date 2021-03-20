package khpi.khpi_olympiad.model.profile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ukrName;

    @OneToMany(mappedBy = "city")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<University> universities;

    public City(String ukrName) {
        this.ukrName = ukrName;
    }

    public void addUniversity(University university) {
        this.universities.add(university);
    }
}
