package dukecooks.model.profile;

import dukecooks.model.profile.person.Person;
import javafx.collections.ObservableList;

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
