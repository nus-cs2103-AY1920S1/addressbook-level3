package seedu.jarvis.model.address;

import javafx.collections.ObservableList;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.financetracker.Purchase;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    ObservableList<Purchase> getPurchaseList();
}
