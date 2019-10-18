package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Reminder;
import seedu.address.model.budget.Budget;
import seedu.address.model.spending.Spending;

import java.util.List;

/**
 * Unmodifiable view of the MoneyGoWhere list
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Spending> getSpendingList();

    /**
     * Returns the Budget of the MoneyGoWhere list.
     */
    Budget getBudget();

    /**
     * Returns a modifiable view of the reminders list.
     */
    List<Reminder> getReminderList();
}
