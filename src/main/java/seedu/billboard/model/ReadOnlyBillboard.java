package seedu.billboard.model;

import javafx.collections.ObservableList;
import seedu.billboard.model.expense.Expense;

/**
 * Unmodifiable view of a Billboard
 */
public interface ReadOnlyBillboard {

    /**
     * Returns an unmodifiable view of the expense list.
     */
    ObservableList<Expense> getExpenses();

}
