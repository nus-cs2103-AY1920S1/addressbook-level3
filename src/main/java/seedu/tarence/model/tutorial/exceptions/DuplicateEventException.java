package seedu.tarence.model.tutorial.exceptions;

/**
 * Signals that the operation will result in duplicate Event.
 */
public class DuplicateEventException extends RuntimeException {
    public DuplicateEventException() {
        super("Operation would result in duplicate Events");
    }
}
