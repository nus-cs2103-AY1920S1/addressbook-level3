package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.binitem.BinItem;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the policies list.
     * This list will not contain any duplicate policies.
     */
    ObservableList<Policy> getPolicyList();

    /**
     * Returns an unmodifiable view of the binItems list.
     * This list will not contain any duplicate BinItems.
     */
    ObservableList<BinItem> getBinItemList();

}
