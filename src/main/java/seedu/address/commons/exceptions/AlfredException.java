package seedu.address.commons.exceptions;

/**
 * Represents an error thrown by the system.
 * {@code AlfredException} is the superclass of the following exception:
 * IllegalValueException, DataConversionException, AlfredRuntimeException.
 */
public abstract class AlfredException extends Exception {

    /**
     * Constructs an instance of {@code AlfredException}.
     *
     * @param message should contain relevant information on the cause of exception.
     */
    public AlfredException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of {@code AlfredException}.
     *
     * @param message should contain relevant information on the cause of exception.
     * @param cause {@code Throwable} contain the type of error or exception thrown.
     */
    public AlfredException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an instance of {@code AlfredException}.
     *
     * @param cause {@code Throwable} contain the type of error or exception thrown.
     */
    public AlfredException(Exception cause) {
        super(cause);
    }


}

