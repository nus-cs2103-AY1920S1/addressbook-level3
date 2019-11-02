package seedu.address.model.budget.exceptions;

/**
 * Signals that the period to be switched to is not a past period.
 */
public class SwitchToFuturePeriodException extends RuntimeException {
    public SwitchToFuturePeriodException() {
        super("Operation would result in switching to a future budget period");
    }
}
