package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.bio.User;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyUserList extends ReadOnlyData {

    /**
     * Returns an unmodifiable view of the user's bio data. This list will not contain any duplicate users.
     */
    ObservableList<User> getUserList();
}
