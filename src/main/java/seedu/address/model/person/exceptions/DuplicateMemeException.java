package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Memes (Memes are considered duplicates if they have the same
 * identity).
 */
public class DuplicateMemeException extends RuntimeException {
    public DuplicateMemeException() {
        super("Operation would result in duplicate persons");
    }
}
