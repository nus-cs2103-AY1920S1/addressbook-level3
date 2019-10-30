package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs when a command that should only be called during a competition session
 * is called outside a competition session.
 */
public class OutOfSessionCommandException extends CommandException {
    public OutOfSessionCommandException() {
        super("Invalid: This command should only be called during a competition session.");
    }
}
