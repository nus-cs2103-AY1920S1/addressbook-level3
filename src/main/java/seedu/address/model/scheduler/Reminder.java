package seedu.address.model.scheduler;

import seedu.address.model.lesson.Lesson;

/**
 * Runnable task which carries out the reminder.
 */
public class Reminder implements Runnable {
    private final Lesson lesson;

    public Reminder(Lesson lesson) {
        this.lesson = lesson;
    }
    public void run() {
        System.out.println(lesson);
    }
}
