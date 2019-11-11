package seedu.jarvis.logic.commands.exceptions;

/**
 * Signals that the operation is unable to find a {@code Command}.
 */
public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException() {
        super("Operation cannot find Command");
    }
}
