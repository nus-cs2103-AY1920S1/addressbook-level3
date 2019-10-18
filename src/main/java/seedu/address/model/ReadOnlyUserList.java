package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.bio.User;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyUserList {

    /**
     * Returns an unmodifiable view of the persons list. This list will not contain any duplicate persons.
     */
    ObservableList<User> getUserList();
}
