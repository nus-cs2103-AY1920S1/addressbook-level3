package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandWordException extends CommandException {
    private String suggestion;

    public CommandWordException(String message, String suggestion) {
        super(message);
        this.suggestion = suggestion;
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public CommandWordException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCommandSuggestion() {
        return this.suggestion;
    }
}
