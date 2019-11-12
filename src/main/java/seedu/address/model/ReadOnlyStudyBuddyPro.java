package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of StudyBuddyPro
 */
public interface ReadOnlyStudyBuddyPro extends ReadOnlyStudyBuddyProFlashcards, ReadOnlyStudyBuddyProCheatSheets,
    ReadOnlyStudyBuddyProNotes {

    /**
     * Returns an unmodifiable view of the flashcards list.
     * This list will not contain any duplicate flashcards.
     */
    ObservableList<Flashcard> getFlashcardList();

    /**
     * Returns an unmodifiable view of the notes list.
     * This list will not contain any duplicate notes.
     */
    ObservableList<Note> getNoteList();

    /**
     * Returns an unmodifiable view of the cheatsheet list.
     * This list will not contain any duplicate cheatsheets.
     */
    ObservableList<CheatSheet> getCheatSheetList();

    /**
     * Returns an unmodifiable view of the tag list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();
}
