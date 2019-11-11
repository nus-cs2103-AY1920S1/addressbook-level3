package seedu.sugarmummy.model.biography;

import javafx.collections.ObservableList;
import seedu.sugarmummy.model.ReadOnlyData;

/**
 * Unmodifiable view of a UserList
 */
public interface ReadOnlyUserList extends ReadOnlyData {

    /**
     * Returns an unmodifiable view of the user's bio data. This list will not contain any duplicate users.
     */
    ObservableList<User> getUserList();
}
