package seedu.ichifund.model.budget.exceptions;

/**
 * Signals that the operation will result in duplicate Budgets (Budgets are considered duplicates if they have the same
 * identity).
 */
public class DuplicateBudgetException extends RuntimeException {
    public DuplicateBudgetException() {
        super("Operation would result in duplicate budgets");
    }
}
