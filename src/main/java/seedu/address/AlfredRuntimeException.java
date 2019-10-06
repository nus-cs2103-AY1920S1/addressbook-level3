package seedu.address;

/**
 * Represents an error during the runtime of the system.
 * Superclass of DuplicateEntityException.
 */
public class AlfredRuntimeException extends RuntimeException {

    /** Constructs an instance of {@code AlfredRuntimeException}.
     *
     * @paraa message should contain relevant information on the cause of runtime exception.
     */
    public AlfredRuntimeException(String message) {
        super(message);
    }
}
