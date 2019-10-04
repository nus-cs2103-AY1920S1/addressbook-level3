package mams.model;

import javafx.collections.ObservableList;
import mams.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyMams {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
