package seedu.address.model.expense.exceptions;

/**
 * Signals that the operation will result in duplicate {@code Expense}s ({@code Expense}s are considered
 * duplicates if they have the same identity).
 */
public class DuplicateExpenseException extends RuntimeException {

    public DuplicateExpenseException() {
        super("Operation would result in duplicate expenses");
    }

}
