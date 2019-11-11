package seedu.moolah.model;

import javafx.collections.ObservableList;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.event.Event;
import seedu.moolah.model.expense.Expense;

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
