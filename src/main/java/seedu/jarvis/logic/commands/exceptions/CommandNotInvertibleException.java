package seedu.jarvis.logic.commands.exceptions;

/**
 * Signals that the operation is involves a Command that has no inverse.
 */
public class CommandNotInvertibleException extends Exception {

    public CommandNotInvertibleException() {
        super("Command Not Invertible");
    }

    public CommandNotInvertibleException(String message) {
        super(message);
    }

    public CommandNotInvertibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
