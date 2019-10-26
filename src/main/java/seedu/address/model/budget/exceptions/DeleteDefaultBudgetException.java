package seedu.address.model.budget.exceptions;

public class DeleteDefaultBudgetException extends RuntimeException {
    public DeleteDefaultBudgetException() {
        super("Default budget cannot be deleted");
    }
}
