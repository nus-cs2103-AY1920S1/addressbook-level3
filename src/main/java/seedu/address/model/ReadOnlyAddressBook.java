package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Note;

/**
 * Unmodifiable view of a lecture note list
 */
public interface ReadOnlyAddressBook {
    /**
     * Returns an unmodifiable view of the lecture note list.
     * This list will not contain any duplicate titles.
     */
    ObservableList<Note> getNoteList();
}
