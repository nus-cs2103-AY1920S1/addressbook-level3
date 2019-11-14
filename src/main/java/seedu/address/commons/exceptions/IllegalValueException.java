package seedu.address.commons.exceptions;

/**
 * Represents an exception thrown to signals that some given data does not fulfill some constraints.
 */
public class IllegalValueException extends AlfredException {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IllegalValueException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public IllegalValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
