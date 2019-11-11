package seedu.address.model.event.exceptions;

/**
 * Signals that the operation will result in duplicate VEvent (VEvents are considered duplicates if they have the same
 * eventName, startDateTime and endDateTime).
 */
public class DuplicateVEventException extends RuntimeException {
    public DuplicateVEventException() {
        super("Operation would result in duplicate vEvents");
    }
}
