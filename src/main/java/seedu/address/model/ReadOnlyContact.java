package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.contact.Contact;

/**
 * Unmodifiable view of a Contact
 */
public interface ReadOnlyContact {

    /**
     * Returns an unmodifiable view of the contacts list.
     * This list will not contain any duplicate contacts.
     */
    ObservableList<Contact> getContactList();

}
