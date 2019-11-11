package seedu.address.model.earnings.earningsexception;

/**
 * Signals that the operation will result in duplicate Earnings
 * (Earnings are considered duplicates if they have the same
 * classId, date, amount and type).
 */
public class DuplicateEarningsException extends RuntimeException {
    public DuplicateEarningsException() {
        super("Operation would result in duplicate earnings");
    }
}
