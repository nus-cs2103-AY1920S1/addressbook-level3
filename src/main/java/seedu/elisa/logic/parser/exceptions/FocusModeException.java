package seedu.elisa.logic.parser.exceptions;

/**
 * Exception that is thrown when attempting to run some operation when in focus mode.
 */
public class FocusModeException extends ParseException {
    public FocusModeException() {
        super("Hey, stay focus on your task!");
    }
}
