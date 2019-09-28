package seedu.address.model.person.exceptions;

import seedu.address.AlfredRuntimeException;

/**
 * Signals that the operation will result in duplicate Entity (Entities are considered duplicates if they have the same identity).
 */
public class DuplicateEntityException extends RuntimeException {
    /**
     * Creates a new instance of DuplicateEntityException according to type of entity.
     * @param type Type of entity.
     */
    public DuplicateEntityException(String type) {
        super("Operation would result in duplicate " + type + " s");
    }
}
