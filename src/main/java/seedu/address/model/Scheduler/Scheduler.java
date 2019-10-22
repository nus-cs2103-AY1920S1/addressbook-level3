package seedu.address.model.scheduler;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;

/**
 * Scheduler class which handles scheduling of lessons.
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
