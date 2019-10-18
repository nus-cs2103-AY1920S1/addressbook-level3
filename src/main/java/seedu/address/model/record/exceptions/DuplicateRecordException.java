package seedu.address.model.record.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateRecordException extends RuntimeException {
    public DuplicateRecordException() {
        super("Operation would result in duplicate records");
    }
}
