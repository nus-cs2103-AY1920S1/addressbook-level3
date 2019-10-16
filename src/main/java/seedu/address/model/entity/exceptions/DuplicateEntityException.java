package seedu.address.model.entity.exceptions;

/**
 * Signals that the operation will result in duplicate Entities(Entities are considered duplicates if they have the same
 * identity).
 */
public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException() {
        super("Operation would result in duplicate entities");
    }
}
