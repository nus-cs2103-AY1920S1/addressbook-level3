package seedu.moolah.model.budget.exceptions;

/**
 * Signals that the operation will result in duplicate budgets (budgets are considered
 * duplicates if they have the same description).
 */
public class DuplicateBudgetException extends RuntimeException {
    public DuplicateBudgetException() {
        super("Operation would result in duplicate budgets");
    }
}
