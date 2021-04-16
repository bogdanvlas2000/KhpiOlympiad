package khpi.khpi_olympiad.model.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String engName;
    private String ukrName;
    private String ukrShortName;
    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private City city;

    @OneToMany(mappedBy = "university", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @JsonIgnoreProperties(value = "usersProfiles")
    private List<Profile> usersProfiles;

    public University(String engName, String ukrName, String ukrShortName, City city) {
        this.engName = engName;
        this.ukrName = ukrName;
        this.ukrShortName = ukrShortName;
        this.city = city;
    }

    public void addUserProfile(Profile userProfile) {
        this.usersProfiles.add(userProfile);
    }
}
