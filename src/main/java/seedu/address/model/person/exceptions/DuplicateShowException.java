package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Shows (Shows are considered duplicates if they have the same
 * identity).
 */
public class DuplicateShowException extends RuntimeException {
    public DuplicateShowException() {
        super("Operation would result in duplicate shows");
    }
}
