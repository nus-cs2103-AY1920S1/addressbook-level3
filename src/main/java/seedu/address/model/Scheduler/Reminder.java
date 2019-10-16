package seedu.address.model.Scheduler;

import java.lang.Runnable;
import seedu.address.model.Lesson.Lesson;

public class Reminder implements Runnable {
    private final Lesson lesson;

    public Reminder(Lesson lesson) {
        this.lesson = lesson;
    }
    public void run() {
        System.out.println(lesson);
    }
}
