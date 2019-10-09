package seedu.billboard.model;

import javafx.collections.ObservableList;
import seedu.billboard.model.person.Expense;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyBillboard {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Expense> getPersonList();

}
