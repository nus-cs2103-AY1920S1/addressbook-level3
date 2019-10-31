package seedu.planner.model;

import javafx.collections.ObservableList;
import seedu.planner.model.contact.Contact;

/**
 * Unmodifiable view of an Contact List
 */
public interface ReadOnlyContact {

    /**
     * Returns an unmodifiable view of the contact list.
     * This list will not contain any duplicate contacts.
     */
    ObservableList<Contact> getContactList();

}
