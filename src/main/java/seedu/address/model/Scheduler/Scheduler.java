package seedu.address.model.Scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import seedu.address.model.Lesson.Lesson;
import seedu.address.model.Lesson.Time;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private final Lesson lesson;

    public Scheduler(Lesson lesson) {
        this.lesson = lesson;
    }

    public void scheduleLesson() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Time time = lesson.getTime();
        Calendar lessonTime = time.getLessonTime();
        long initialDelay = lessonTime.getTimeInMillis() - System.currentTimeMillis();
        Reminder reminder = new Reminder(lesson);
        if (lesson.getIsRepeat()) {
            long period = TimeUnit.DAYS.toMillis(7);
            scheduler.scheduleAtFixedRate(reminder, initialDelay, period, TimeUnit.MILLISECONDS);
        } else {
            scheduler.schedule(reminder, initialDelay, TimeUnit.MILLISECONDS);
        }
    }
}
