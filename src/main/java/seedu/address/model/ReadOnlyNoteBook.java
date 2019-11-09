package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.note.MultipleSortByCond;
import seedu.address.model.note.Note;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyNoteBook {

    /**
     * Returns an unmodifiable view of the notes list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Note> getNoteList();

    MultipleSortByCond getSortByConds();

}
