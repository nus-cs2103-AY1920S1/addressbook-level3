package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook extends Observable {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the workers list.
     * This list will not contain any duplicate workers.
     */
    ObservableList<Worker> getWorkerList();

    /**
     * Returns an unmodifiable view of the bodies list.
     * This list will not contain any duplicate bodies.
     */
    ObservableList<Body> getBodyList();

    /**
     * Returns an unmodifiable view of the fridges list.
     * This list will not contain any duplicate fridges.
     */
    ObservableList<Fridge> getFridgeList();

}
