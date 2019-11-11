package seedu.module.model.module.exceptions;

/**
 * Signals that the date and time is in an invalid format.
 */
public class DeadlineParseException extends RuntimeException {
    public DeadlineParseException(String message) {
        super(message);
    }
}
