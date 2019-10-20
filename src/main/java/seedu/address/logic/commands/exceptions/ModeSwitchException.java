package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link seedu.address.logic.commands.Command}.
 */
public class ModeSwitchException extends CommandException {
    public ModeSwitchException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public ModeSwitchException(String message, Throwable cause) {
        super(message, cause);
    }
}
