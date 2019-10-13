package seedu.flashcard.logic.commands.exceptions;

/**
 * Represents an error during the execution of a command.
 */
public class CommandException extends Exception {

    public CommandException(String message) {
        super(message);
    }
}
