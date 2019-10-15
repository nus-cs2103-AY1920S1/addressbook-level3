package seedu.exercise.model.schedule.exceptions;

/**
 * Signals that the operation will result in duplicate schedules (Schedules are considered duplicates if they have
 * the same date).
 */
public class DuplicateScheduleException extends RuntimeException {
    public DuplicateScheduleException() {
        super("Operation would result in duplicate schedules");
    }
}
