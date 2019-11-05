package seedu.address.model.performance.exceptions;

/**
 * Signals that the operation will result in duplicate Events (Events are considered duplicates if they have the same
 * name).
 */
public class DuplicateEventException extends RuntimeException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events");
    }
}
