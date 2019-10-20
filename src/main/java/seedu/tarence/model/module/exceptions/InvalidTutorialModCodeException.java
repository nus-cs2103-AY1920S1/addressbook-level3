package seedu.tarence.model.module.exceptions;

/**
 * Signals that the operation is unable to find the specified module.
 */
public class InvalidTutorialModCodeException extends RuntimeException {
    public InvalidTutorialModCodeException() {
        super ("Operation cannot add tutorial to module due to modcode mismatch.");
    }
}
