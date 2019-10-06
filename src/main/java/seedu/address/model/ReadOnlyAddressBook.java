package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.entity.body.Body;
<<<<<<< HEAD
=======
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Person;
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Body> getBodyList();

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

}
