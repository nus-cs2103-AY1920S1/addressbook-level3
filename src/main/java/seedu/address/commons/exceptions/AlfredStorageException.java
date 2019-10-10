package seedu.address.commons.exceptions;

/**
 * Wrapper Exception for Storage Exceptions.
 */
public class AlfredStorageException extends AlfredException {
    /**
     * Constructor.
     *
     * @param message
     */
    public AlfredStorageException(String message) {
        super(message);
    }

    /**
     * Constructor which takes an exception.
     *
     * @param cause
     */
    public AlfredStorageException(Exception cause) {
        super(cause);
    }
}
