package seedu.tarence.model.tutorial.exceptions;

/**
 * Signals that the operation is unable to find the specified Week.
 */
public class WeekNotFoundException extends RuntimeException {
    public WeekNotFoundException() {
        super("Operation is unable to find the specified Week.");
    }
}
