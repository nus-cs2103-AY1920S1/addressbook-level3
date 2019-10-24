package seedu.address.logic.commands.exceptions;

import seedu.address.logic.commands.Command;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class UnknownCommandResultTypeException extends Exception {

    public UnknownCommandResultTypeException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code UnknownCommandResultTypeException} with the
     * specified detail {@code message} and {@code cause}.
     */
    public UnknownCommandResultTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
