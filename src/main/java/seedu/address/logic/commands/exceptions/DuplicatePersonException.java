package seedu.address.logic.commands.exceptions;

/**
 * Represents a duplicate error which occurs during execution of a {@link AddCommand}.
 */
public class DuplicatePersonException extends CommandException {
    public DuplicatePersonException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public DuplicatePersonException(String message, Throwable cause) {
        super(message, cause);
    }
}
