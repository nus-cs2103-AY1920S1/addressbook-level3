package seedu.address.logic.commands.exceptions;

/**
 * Represents a duplicate error which occurs during execution of a {@link AddPolicyCommand}.
 */
public class DuplicatePolicyWithMergeException extends CommandException {
    public DuplicatePolicyWithMergeException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public DuplicatePolicyWithMergeException(String message, Throwable cause) {
        super(message, cause);
    }
}
