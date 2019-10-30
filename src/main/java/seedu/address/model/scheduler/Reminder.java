package seedu.address.model.scheduler;

import seedu.address.model.lesson.Lesson;

/**
 * Runnable task which carries out the reminder.
 */
public class Reminder implements Runnable {
    private final Lesson lesson;
    private String title;
    private String details;

    public Reminder(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getTitle() {
        return title;
    }
    public String getDetails() {
        return details;
    }
    public void run() {

    }

    /**
     * Simple method to check if reminder is a duplicate
     * Either exact same java obj or same content
     * @param otherReminder
     * @return
     */
    public boolean isSameReminder(Reminder otherReminder) {
        if (this == otherReminder) {
            return true;
        }
        return otherReminder != null
                && otherReminder.getTitle().equals(getTitle())
                && (otherReminder.getDetails().equals(getDetails()));
    }
}
