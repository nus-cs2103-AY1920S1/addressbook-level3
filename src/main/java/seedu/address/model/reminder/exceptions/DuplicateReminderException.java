package seedu.address.model.reminder.exceptions;

/**
 * Signals that the operation will result in duplicate Reminders
 * (Reminders are considered duplicates if they have the same
 * description and time).
 */
public class DuplicateReminderException extends Throwable {
    public DuplicateReminderException() {
        super("Operation would result in duplicate earnings");
    }
}
