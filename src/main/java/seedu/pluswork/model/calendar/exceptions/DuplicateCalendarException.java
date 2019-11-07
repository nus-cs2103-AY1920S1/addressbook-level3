package seedu.pluswork.model.calendar.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCalendarException extends RuntimeException {
    public DuplicateCalendarException() {
        super("Operation would result in duplicate calendars");
    }
}
