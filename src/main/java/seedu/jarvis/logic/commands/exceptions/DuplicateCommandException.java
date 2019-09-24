package seedu.jarvis.logic.commands.exceptions;

/**
 * Signals that the operation will result in duplicate Commands (Commands are considered duplicates if they are that
 * same Command Object instance).
 */
public class DuplicateCommandException extends Exception {

    public DuplicateCommandException() {
        super("Operation would result in duplicate commands");
    }

    public DuplicateCommandException(String message) {
        super(message);
    }

    public DuplicateCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
