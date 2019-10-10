package seedu.address.model.spending.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateSpendingException extends RuntimeException {
    public DuplicateSpendingException() {
        super("Operation would result in duplicate persons");
    }
}
