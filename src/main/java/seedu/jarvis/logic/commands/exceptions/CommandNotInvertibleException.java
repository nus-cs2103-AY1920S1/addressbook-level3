package seedu.jarvis.logic.commands.exceptions;

/**
 * Signals that the operation is involves a {@code Command} that has no inverse.
 */
public class CommandNotInvertibleException extends RuntimeException {
    public CommandNotInvertibleException() {
        super("Operation involves a command that is not invertible");
    }
}
