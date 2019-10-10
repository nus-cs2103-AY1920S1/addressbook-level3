package seedu.address.model.tag.exceptions;

/**
 * Signals that the operation involves an invalid tag.
 */

public class InvalidTagException extends RuntimeException {

    public InvalidTagException(String message) {
        super(message);
    }

}
