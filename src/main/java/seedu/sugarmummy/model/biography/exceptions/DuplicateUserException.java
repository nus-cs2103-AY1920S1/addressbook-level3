package seedu.sugarmummy.model.biography.exceptions;

/**
 * Signals that the operation will result in duplicate users (Users are considered duplicates if they have the same
 * biography information).
 */
public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super("Operation would result in duplicate users.");
    }
}
