package khpi.khpi_olympiad.model.event;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserEventKey implements Serializable {
    private int userId;
    private int eventId;
}
