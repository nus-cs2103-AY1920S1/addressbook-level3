package seedu.address.calendar.model.event.exceptions;

/**
 * Represents an error when the user tries to add two events that are of the same type.
 */
public class DuplicateEventException extends RuntimeException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events");
    }
}
