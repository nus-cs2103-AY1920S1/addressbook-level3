package seedu.address.model.expense.exceptions;

/**
 * Exception thrown when an {@code Expense} is not found in an operation.
 */
public class ExpenseNotRemovableException extends RuntimeException {

    public ExpenseNotRemovableException() {
        super("Operation cannot be completed as the expense cannot be removed by user directly");
    }

}
