package seedu.address.model.exceptions;

/**
 * Signals that an invalid EventSource was constructed.
 */
public class InvalidEventSourceException extends RuntimeException {

    public InvalidEventSourceException(String message) {
        super(message);
    }
}
