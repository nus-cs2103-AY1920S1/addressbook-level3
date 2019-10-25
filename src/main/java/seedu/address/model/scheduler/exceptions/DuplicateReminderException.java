package seedu.address.model.scheduler.exceptions;

/**
 * Signals that the operation will result in duplicate Reminders (Reminders are considered duplicates
 * if they have the same name).
 */
public class DuplicateReminderException extends RuntimeException {
}
