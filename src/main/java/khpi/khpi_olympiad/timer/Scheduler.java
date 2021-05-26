package khpi.khpi_olympiad.timer;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;

@Component
public class Scheduler {
    private Timer timer = new Timer();

    public void createTask(SchedulerTask task) {
        Date taskDate = Date.from(task.getEvent().getEventDate()
                .atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(task, taskDate);
    }

}
