package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.entity.body.Body;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Body> getBodyList();

}
