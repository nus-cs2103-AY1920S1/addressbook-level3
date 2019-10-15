package seedu.address.profile;

import javafx.collections.ObservableList;
import seedu.address.profile.person.Person;

/**
 * Unmodifiable view of Duke Cooks
 */
public interface ReadOnlyDukeCooks {

    /**
     * Returns an unmodifiable view of the user profile in the list.
     * This list will only contain one instance of profile.
     */
    ObservableList<Person> getUserProfileList();

}
