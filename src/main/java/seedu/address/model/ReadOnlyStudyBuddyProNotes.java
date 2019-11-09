package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of StudyBuddyPro's notes
 */
public interface ReadOnlyStudyBuddyProNotes {

    /**
     * Returns an unmodifiable view of the notes list.
     * This list will not contain any duplicate notes.
     */
    ObservableList<Note> getNoteList();

    /**
     * Returns an unmodifiable view of the tag list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();
}
