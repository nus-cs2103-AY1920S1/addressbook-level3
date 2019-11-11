package seedu.address.model.borrower.exceptions;

/**
 * Signals that the operation will result in duplicate Borrowers (Borrowers are considered duplicates if they have the
 * same identity).
 */
public class DuplicateBorrowerException extends RuntimeException {
    public DuplicateBorrowerException() {
        super("Operation would result in duplicate borrowers");
    }
}
