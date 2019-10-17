package seedu.address.model.earnings.earningsexception;

/**
 * Signals that the operation will result in duplicate Earnings
 * (Earnings are considered duplicates if they have the same
 * module, date and amount).
 */
public class DuplicateEarningsException extends RuntimeException {
    public DuplicateEarningsException() {
        super("Operation would result in duplicate earnings");
    }
}
