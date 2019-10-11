package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

public interface ReadOnlyStudentsList {

    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getStudentsList();
}
