package seedu.address.ui.scheduler;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;

/**
 * scheduler class which handles scheduling of lessons.
 */
public class Scheduler {
    private final Lesson lesson;

    public Scheduler(Lesson lesson) {
        this.lesson = lesson;
    }

    /**
     * Method which schedules the lesson depending on whether its repeat or not.
     * Uses ScheduledExecutorService to schedule lesson.
     */
    public void scheduleLesson(Runnable task) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Time time = lesson.getStartTime();
        Calendar lessonTime = time.getTime();
        long initialDelay = lessonTime.getTimeInMillis() - System.currentTimeMillis();
        if (lesson.getIsRepeat()) {
            long period = TimeUnit.DAYS.toMillis(7);
            scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
        } else {
            scheduler.schedule(task, initialDelay, TimeUnit.MILLISECONDS);
        }
    }
}
