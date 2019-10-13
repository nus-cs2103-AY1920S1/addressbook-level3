package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Entry> getEntryList();

    ObservableList<Expense> getExpenseList();

    ObservableList<Income> getIncomeList();
}
