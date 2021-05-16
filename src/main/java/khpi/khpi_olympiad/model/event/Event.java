package khpi.khpi_olympiad.model.event;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import khpi.khpi_olympiad.model.auth.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime eventDate;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @ManyToMany(mappedBy = "events")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> subscribers = new HashSet<>();
}
