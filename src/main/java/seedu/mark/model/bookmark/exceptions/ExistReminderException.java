package seedu.mark.model.bookmark.exceptions;

/**
 * Signals that the specified bookmark already has a reminder.
 */
public class ExistReminderException extends RuntimeException{
    public ExistReminderException() {
        super("This bookmark already has a reminder. Cannot have more than one reminder in a bookmark.");
    }
}
