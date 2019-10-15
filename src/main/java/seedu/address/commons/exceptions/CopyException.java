package seedu.address.commons.exceptions;

/**
 * Represents an error during the copying of data
 */
public class CopyException extends Exception {
    public CopyException(String message) {
        super(message);
    }

    public CopyException(Throwable cause) {
        super(cause);
    }

    public CopyException(String message, Throwable cause) {
        super(message, cause);
    }
}
