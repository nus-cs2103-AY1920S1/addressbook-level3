package seedu.moolah.model;

import javafx.collections.ObservableList;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;

/**
 * Unmodifiable view of a MooLah
 */
public interface ReadOnlyMooLah {

    /**
     * Creates a read-only copy of the current MooLah.
     */
    ReadOnlyMooLah copy();

    /**
     * Returns an unmodifiable view of the expenses list.
     * This list will not contain any duplicate expenses.
     */
    ObservableList<Expense> getExpenseList();

    /**
     * Returns an unmodifiable view of the budgets list.
     * This list will not contain any duplicate budgets.
     */
    ObservableList<Budget> getBudgetList();

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

    String getPrimaryBudgetName();

}
