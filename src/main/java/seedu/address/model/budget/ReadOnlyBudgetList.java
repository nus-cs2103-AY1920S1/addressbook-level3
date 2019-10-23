package seedu.address.model.budget;

import javafx.collections.ObservableList;

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
