package budgetbuddy.model.loan.exceptions;

/**
 * Signals that the operation will result in duplicate Loans.
 * loans are considered duplicates if all their fields (except Status) have the same value.
 */
public class DuplicateLoanException extends RuntimeException {
    public DuplicateLoanException() {
        super("Operation would result in duplicate loans.");
    }
}
