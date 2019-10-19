package seedu.address.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;

public class AutoRescheduleManager {
    public static AutoRescheduleManager manager;
    public static Timer timer;

    private AutoRescheduleManager() {}

    public static AutoRescheduleManager getInstance() {
        if (manager == null) {
            manager = new AutoRescheduleManager();
            timer = new Timer();
        }
        return manager;
    }

    public void add(RescheduleTask task) {
        try {
            Duration delay = Duration.between(LocalDateTime.now(), task.getStartTime());
            timer.scheduleAtFixedRate(task, delay.toMillis(), task.getLongPeriod());
            System.out.println("Should only add once");
        } catch (Exception e) {
            System.out.println("Fail to add rescheduleTask: " + e.getMessage());
        }
    }

    public void shutdown() {
        timer.cancel();
    }

}
