package seedu.billboard.model.expense.exceptions;

/**
 * Signals that the operation will result in duplicate Expenses (Expenses are considered duplicates if they are
 * considered equals under their @{code equals} method.
 */
public class DuplicateExpenseException extends RuntimeException {
    public DuplicateExpenseException() {
        super("Operation would result in duplicate expenses");
    }
}
