package tagline.model.note;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyNoteBook {

    /**
     * Returns an unmodifiable view of the notes list.
     * This list will not contain any duplicate notes.
     */
    ObservableList<Note> getNoteList();

}
