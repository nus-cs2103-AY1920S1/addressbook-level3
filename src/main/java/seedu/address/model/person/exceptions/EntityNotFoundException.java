package seedu.address.model.person.exceptions;

import seedu.address.AlfredRuntimeException;

/**
 * Signals that the operation is unable to find the specified Entity.
 * This is due to incorrect index specified.
 */
public class EntityNotFoundException extends AlfredRuntimeException {

    /**
     * Creates a new instance of EntityNotFoundException according to type of entity.
     * @param type Type of entity.
     */
    public EntityNotFoundException(String type) {
        super(type + "is not found");
    }
}
