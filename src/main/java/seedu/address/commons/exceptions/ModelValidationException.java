package seedu.address.commons.exceptions;

/**
 * This error is thrown when the model lists are not in sync.
 */
public class ModelValidationException extends AlfredModelException {
    public ModelValidationException(String message) {
        super(message);
    }
}
