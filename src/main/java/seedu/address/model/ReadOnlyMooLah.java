package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Expense;

/**
 * Unmodifiable view of a MooLah
 */
public interface ReadOnlyMooLah {

    /**
     * Returns an unmodifiable view of the expenses list.
     * This list will not contain any duplicate expenses.
     */
    ObservableList<Expense> getExpenseList();

    ObservableList<Budget> getBudgetList();

    ObservableList<Event> getEventList();

    String getPrimaryBudgetName();
}
