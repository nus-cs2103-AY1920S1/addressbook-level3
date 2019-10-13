package seedu.flashcard.logic.commands.exceptions;

/**
 * Represents an error during the execution of a command.
 */
public class CommandException extends Exception {

    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
