package seedu.deliverymans.model.addressbook;

import javafx.collections.ObservableList;
import seedu.deliverymans.model.addressbook.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
