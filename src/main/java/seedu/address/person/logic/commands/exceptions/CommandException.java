package seedu.address.person.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link seedu.address.person.logic.commands.Command}.
 */
public class CommandException extends Exception {
    private String message;
    public CommandException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
