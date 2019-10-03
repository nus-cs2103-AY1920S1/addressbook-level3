package seedu.jarvis.logic.commands.exceptions;

/**
 * Signals that the operation is unable to find the Command.
 */
public class CommandNotFoundException extends Exception {
    public CommandNotFoundException() {
        super("Command Not Found");
    }

    public CommandNotFoundException(String message) {
        super(message);
    }

    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
