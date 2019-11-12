package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of finding prefixes in arguments.
 */
public class DuplicatePrefixException extends Exception {
    public DuplicatePrefixException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DuplicatePrefixException} with the specified detail {@code message} and {@code cause}.
     */
    public DuplicatePrefixException(String message, Throwable cause) {
        super(message, cause);
    }
}
