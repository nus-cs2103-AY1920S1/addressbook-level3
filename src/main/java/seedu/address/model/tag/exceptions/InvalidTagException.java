package seedu.address.model.tag.exceptions;

/**
 * Signals that the operation will involve an invalid tag.
 */

public class InvalidTagException extends RuntimeException {

    public InvalidTagException(String message) {
        super(message);
    }

}
