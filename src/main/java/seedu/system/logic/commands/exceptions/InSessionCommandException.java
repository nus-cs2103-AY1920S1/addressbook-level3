package seedu.system.logic.commands.exceptions;

/**
 * Represents an error which occurs when a {@link Command} that should only be called outside a competition session
 * is called during a competition session.
 */
public class InSessionCommandException extends CommandException {
    public InSessionCommandException() {
        super("Invalid: This command should only be called outside of a competition session.");
    }
}
