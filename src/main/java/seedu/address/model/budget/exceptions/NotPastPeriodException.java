package seedu.address.model.budget.exceptions;

/**
 * Signals that the period to be switched to is not a past period.
 */
public class NotPastPeriodException extends RuntimeException {
    public NotPastPeriodException() {
        super("The period you want to switch to is not a past period.");
    }
}
