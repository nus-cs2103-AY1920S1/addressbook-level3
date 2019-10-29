package seedu.address.model.transaction.exceptions;

/**
 * Signals that the operation will result in duplicate Budgets (Budgets are considered duplicate if they have the same
 * details).
 */
public class DuplicateBudgetException extends RuntimeException {
    public DuplicateBudgetException() {
        super("Operation would result in duplicate budgets");
    }
}
