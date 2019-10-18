package seedu.address.model.profile;

import javafx.collections.ObservableList;
import seedu.address.model.profile.person.Person;

/**
 * Unmodifiable view of the User Profile
 */
public interface ReadOnlyUserProfile {

    /**
     * Returns an unmodifiable view of the user profile in the list.
     * This list will only contain one instance of profile.
     */
    ObservableList<Person> getUserProfileList();

}
