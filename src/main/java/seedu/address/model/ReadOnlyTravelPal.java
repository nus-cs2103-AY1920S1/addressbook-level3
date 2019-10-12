package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.trip.Trip;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTravelPal {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();
    ObservableList<Trip> getTripList();


}
