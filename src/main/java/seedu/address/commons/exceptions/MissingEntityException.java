package seedu.address.commons.exceptions;

/**
 * MissingEntityException is thrown when a CRUD operation targets a missing entity.
 */
public class MissingEntityException extends AlfredModelException {
    public MissingEntityException(String message) {
        super(message);
    }
}
