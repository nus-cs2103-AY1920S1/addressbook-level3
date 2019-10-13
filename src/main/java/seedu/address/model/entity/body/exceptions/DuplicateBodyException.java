package seedu.address.model.entity.body.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateBodyException extends RuntimeException {
    public DuplicateBodyException() {
        super("Operation would result in duplicate persons");
    }
}
