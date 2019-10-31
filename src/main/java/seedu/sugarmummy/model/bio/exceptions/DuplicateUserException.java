package seedu.sugarmummy.model.bio.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super("Operation would result in duplicate persons");
    }
}
