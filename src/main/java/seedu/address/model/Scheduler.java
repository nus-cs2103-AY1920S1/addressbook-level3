package seedu.address.model;

import java.util.concurrent.ScheduledExecutorService;
import seedu.address.model.Lesson.Lesson;
import seedu.address.model.Lesson.Time;
import java.util.Calendar;

public class Scheduler {
    private final Lesson lesson;

    public Scheduler(Lesson lesson) {
        this.lesson = lesson;
    }

    public void scheduleLesson() {
        Time lessonTime = lesson.getTime();

    }
}
