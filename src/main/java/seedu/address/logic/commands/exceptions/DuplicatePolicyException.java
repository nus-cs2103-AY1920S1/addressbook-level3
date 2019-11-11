package seedu.address.logic.commands.exceptions;

/**
 * Represents a duplicate error which occurs during execution of a {@link AddPolicyCommand}.
 */
public class DuplicatePolicyException extends CommandException {
    public DuplicatePolicyException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public DuplicatePolicyException(String message, Throwable cause) {
        super(message, cause);
    }
}
