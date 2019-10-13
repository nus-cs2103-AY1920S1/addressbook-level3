package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.incident.Incident;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.Vehicle;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the vehicles list.
     * This list will not contain any duplicate vehicles.
     */
    ObservableList<Incident> getIncidentList();

    /**
     * Returns an unmodifiable view of the vehicles list.
     * This list will not contain any duplicate vehicles.
     */
    ObservableList<Vehicle> getVehicleList();

}
