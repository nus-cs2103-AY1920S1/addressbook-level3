package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a Command.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
