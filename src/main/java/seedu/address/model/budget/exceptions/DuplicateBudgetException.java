package seedu.address.model.budget.exceptions;

public class DuplicateBudgetException extends RuntimeException {
    public DuplicateBudgetException() {
        super("Operation would result in duplicate budgets");
    }
}
