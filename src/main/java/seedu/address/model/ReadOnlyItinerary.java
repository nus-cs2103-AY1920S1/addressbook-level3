package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyItinerary {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the activities list.
     * This list will not contain any duplicate activities.
     */
    ObservableList<Activity> getActivityList();

}
