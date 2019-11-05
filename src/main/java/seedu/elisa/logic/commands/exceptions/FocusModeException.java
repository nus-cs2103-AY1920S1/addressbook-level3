package seedu.elisa.logic.commands.exceptions;

/**
 * Exception that is thrown when attempting to run some operation when in focus mode.
 */
public class FocusModeException extends CommandException {
    public FocusModeException() {
        super("Hey, stay focus on your task!");
    }
}
