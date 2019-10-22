package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.note.Note;

/**
 * Unmodifiable view of note.
 */
public interface ReadOnlyNote {
    ObservableList<Note> getNoteList();
}
