package seedu.moolah.model.budget.exceptions;

/**
 * Signals that the operation will result in the default budget being deleted.
 */
public class DeleteDefaultBudgetException extends RuntimeException {
    public DeleteDefaultBudgetException() {
        super("Default budget cannot be deleted");
    }
}
