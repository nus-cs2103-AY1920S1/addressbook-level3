package seedu.address.model.event.exceptions;

/**
 * Signals that the operation will result in duplicate Employees
 * (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateEventException extends RuntimeException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events");
    }
}
