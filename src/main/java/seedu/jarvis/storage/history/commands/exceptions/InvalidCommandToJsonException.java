package seedu.jarvis.storage.history.commands.exceptions;

/**
 * Represents an error which occurs during construction a Jackson-friendly version of a {@link Command}.
 */
public class InvalidCommandToJsonException extends Exception {

    /**
     * Constructs a new {@code InvalidCommandToJsonException} with the specified detail {@code message}.
     *
     * @param message Message of the exception.
     */
    public InvalidCommandToJsonException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code InvalidCommandToJsonException} with the specified detail {@code message} and
     * {@code cause}.
     *
     * @param message Message of the exception.
     * @param cause Cause of the exception.
     */
    public InvalidCommandToJsonException(String message, Throwable cause) {
        super(message, cause);
    }

}
