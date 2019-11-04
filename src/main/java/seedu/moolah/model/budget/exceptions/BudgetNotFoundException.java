package seedu.moolah.model.budget.exceptions;

/**
 * Signals that the operation is unable to find the specified budget.
 */
public class BudgetNotFoundException extends RuntimeException {
    public BudgetNotFoundException() {
        super("Budget is not found in MooLah");
    }
}
