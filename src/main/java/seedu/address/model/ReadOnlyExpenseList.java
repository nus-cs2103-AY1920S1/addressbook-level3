package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.expense.Expense;

/**
 * Unmodifiable view of an expense list
 */
public interface ReadOnlyExpenseList {

    /**
     * Returns an unmodifiable view of the expenses list.
     * This list will not contain any duplicate expenses.
     */
    ObservableList<Expense> getExpenseList();
}
