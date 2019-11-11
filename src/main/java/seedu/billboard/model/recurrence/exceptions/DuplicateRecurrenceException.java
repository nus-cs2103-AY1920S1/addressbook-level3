package seedu.billboard.model.recurrence.exceptions;

/**
 * Signals that the operation will result in duplicate Recurrences (Expenses are considered duplicates if they are
 * considered equals under their @{code equals} method.
 */
public class DuplicateRecurrenceException extends RuntimeException {
    public DuplicateRecurrenceException() {
        super("Operation would result in duplicate expenses");
    }
}
