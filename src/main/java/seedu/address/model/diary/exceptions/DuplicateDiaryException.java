package seedu.address.model.diary.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDiaryException extends RuntimeException {
    public DuplicateDiaryException() {
        super("Operation would result in duplicate persons");
    }
}
