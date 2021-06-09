package khpi.khpi_olympiad.sheduler;

import khpi.khpi_olympiad.model.event.Event;
import khpi.khpi_olympiad.model.event.EventStatus;
import khpi.khpi_olympiad.repository.event.EventRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.TimerTask;

@AllArgsConstructor
@Data
public class SchedulerTask extends TimerTask {
    private Event event;
    private EventRepository eventRepository;

    @Override
    public void run() {
        if (eventRepository.findById(event.getId()) != null) {
            event.setEventStatus(EventStatus.NOT_ACTIVE);
            eventRepository.save(event);
            System.out.println(event.getTitle() + " deactivated!");
        }
    }
}
