package seedu.address.model.note;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a notes record.
 */
public interface ReadOnlyNotesRecord {

    /**
     * Returns an unmodifiable view of the notes list.
     * This list will not contain any duplicate note as compared by {@code Note#isSameNote(Note)}.
     */
    ObservableList<Note> getNotesList();

}
