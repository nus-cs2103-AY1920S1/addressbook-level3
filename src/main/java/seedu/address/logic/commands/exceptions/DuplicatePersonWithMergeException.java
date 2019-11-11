package seedu.address.logic.commands.exceptions;

/**
 * Represents a duplicate error which occurs during execution of a {@link AddCommand}.
 */
public class DuplicatePersonWithMergeException extends CommandException {
    public DuplicatePersonWithMergeException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public DuplicatePersonWithMergeException(String message, Throwable cause) {
        super(message, cause);
    }
}
