package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Entries (Entries are considered duplicates if they have the same
 * identity).
 */
public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException() {
        super("Operation would result in duplicate entries");
    }
}
