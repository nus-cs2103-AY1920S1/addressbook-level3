package seedu.address.model.events.exceptions;

/**
 * Signals that the operation will result in duplicate events (Events are considered duplicates if they involve the
 * same person at the same timing).
 */
public class DuplicateEventException extends RuntimeException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events");
    }
}
