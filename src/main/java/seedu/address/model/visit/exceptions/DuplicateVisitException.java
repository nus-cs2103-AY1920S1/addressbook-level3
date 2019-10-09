package seedu.address.model.visit.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateVisitException extends RuntimeException {
    public DuplicateVisitException() {
        super("Operation would result in duplicate persons");
    }
}
