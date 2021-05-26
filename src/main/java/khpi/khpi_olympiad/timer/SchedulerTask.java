package khpi.khpi_olympiad.timer;

import khpi.khpi_olympiad.model.event.Event;
import khpi.khpi_olympiad.model.event.EventStatus;
import khpi.khpi_olympiad.repository.event.EventRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.TimerTask;

@AllArgsConstructor
@Data
public class SchedulerTask extends TimerTask {
    private EventRepository eventRepository;
    private Event event;

    @Override
    public void run() {
        event.setEventStatus(EventStatus.NOT_ACTIVE);
        eventRepository.save(event);
    }
}
