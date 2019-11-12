package seedu.address.model.password.exceptions;

/**
 * Signals that the operation will result in duplicate Password (Passwords are considered duplicates if they
 * have the same identity).
 */
public class DuplicatePasswordException extends RuntimeException {
    public DuplicatePasswordException() {
        super("Operation would result in duplicate passwords");
    }

}
