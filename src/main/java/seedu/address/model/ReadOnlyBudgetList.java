package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;

/**
 * Unmodifiable view of an expense list
 */
public interface ReadOnlyBudgetList {

    /**
     * Returns an unmodifiable view of the expenses list.
     * This list will not contain any duplicate expenses.
     */
    ObservableList<Budget> getBudgetList();
}
