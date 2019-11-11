package seedu.tarence.model.tutorial.exceptions;

/**
 * Signals that the operation will result in duplicate Event.
 */
public class EventOverlapException extends RuntimeException {
    public EventOverlapException() {
        super("Operation would result in overlapping Events");
    }
}
