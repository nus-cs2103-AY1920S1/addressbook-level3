package seedu.address.model.note.exception;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateNoteTitleException extends RuntimeException {
    public DuplicateNoteTitleException() {
        super("Operation would result in duplicate notes!");
    }
}
