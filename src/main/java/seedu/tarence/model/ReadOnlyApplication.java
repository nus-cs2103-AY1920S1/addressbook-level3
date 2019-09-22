package seedu.tarence.model;

import javafx.collections.ObservableList;
import seedu.tarence.model.person.Person;

/**
 * Unmodifiable view of T.A.rence
 */
public interface ReadOnlyApplication {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
