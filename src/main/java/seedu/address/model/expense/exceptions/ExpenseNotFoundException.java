package seedu.address.model.expense.exceptions;

/**
 * Exception thrown when an {@code Expense} is not found in an operation.
 */
public class ExpenseNotFoundException extends RuntimeException {

    public ExpenseNotFoundException() {
        super("Operation cannot be completed as the required expense is not found");
    }

}
