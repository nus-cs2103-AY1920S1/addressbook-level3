package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Expense;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the expenses list.
     * This list will not contain any duplicate expenses.
     */
    ObservableList<Expense> getExpenseList();

    ObservableList<Event> getEventList();
}
