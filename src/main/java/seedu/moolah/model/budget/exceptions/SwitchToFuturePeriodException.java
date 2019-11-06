package seedu.moolah.model.budget.exceptions;

/**
 * Signals that the period to be switched to is a future period.
 */
public class SwitchToFuturePeriodException extends RuntimeException {
    public SwitchToFuturePeriodException() {
        super("Operation would result in switching to a future budget period");
    }
}
