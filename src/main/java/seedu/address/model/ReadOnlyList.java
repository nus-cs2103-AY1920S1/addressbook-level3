package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.person.Person;

/**
 * Represents a List of Interviewees/Interviewers that can only be read.
 */
public interface ReadOnlyList<T extends Person> {

    /**
     * Returns an unmodifiable view of the Interviewer/Interviewee list.
     * This list will not contain any duplicate Interviewers/Interviewees.
     */
    ObservableList<T> getEntityList();
}
