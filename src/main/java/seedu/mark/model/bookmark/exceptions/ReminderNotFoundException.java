package seedu.mark.model.bookmark.exceptions;

/**
 * Signals that the operation is unable to find the specified reminder.
 */
public class ReminderNotFoundException extends RuntimeException {
    public ReminderNotFoundException() {
        super("This reminder does not exist.");
    }
}

