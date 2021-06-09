package khpi.khpi_olympiad.sheduler;

import khpi.khpi_olympiad.repository.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;

@Component
public class Scheduler {

    private Timer timer;

    private EventRepository eventRepository;

    @Autowired
    public Scheduler(EventRepository eventRepository) {
        timer = new Timer();
        var events = eventRepository.findAll();
        for (var e : events) {
            var task = new SchedulerTask(e, eventRepository);
            this.addTask(task);
        }
    }

    public void addTask(SchedulerTask task) {

        Date taskDate = Date.from(task.getEvent().getEventDate()
                .atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(task, taskDate);
    }

}
