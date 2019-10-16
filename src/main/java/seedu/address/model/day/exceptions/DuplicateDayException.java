package seedu.address.model.day.exceptions;

/**
 * Signals that the operation will result in duplicate Days (Days are considered if they have the same activities).
 */
public class DuplicateDayException extends RuntimeException {
    public DuplicateDayException() {
        super("Operation would result in duplicate days");
    }
}
