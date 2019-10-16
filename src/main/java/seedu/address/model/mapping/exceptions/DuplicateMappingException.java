package seedu.address.model.mapping.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateMappingException extends RuntimeException {
    public DuplicateMappingException() {
        super("Operation would result in duplicate tasks");
    }
}
