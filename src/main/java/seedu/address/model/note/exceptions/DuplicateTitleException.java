package seedu.address.model.note.exceptions;

/**
 * Signals that the operation will result in lecture notes with duplicate titles.
 */
public class DuplicateTitleException extends RuntimeException {
    public DuplicateTitleException() {
        super("Operation would result in lecture notes with duplicate titles");
    }
}
