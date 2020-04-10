package seedu.address.model.performance.exceptions;

/**
 * Signals that the operation cannot proceed as the Event to be deleted does not exist (Events are considered
 * the same if they have the same name).
 */
public class NoEventException extends RuntimeException {
    public NoEventException() {
        super("Operation cannot proceed as event to be deleted does not exist");
    }
}
