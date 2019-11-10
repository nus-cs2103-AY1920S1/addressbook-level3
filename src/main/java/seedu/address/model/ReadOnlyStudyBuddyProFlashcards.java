package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of StudyBuddyPro's flashcards
 */
public interface ReadOnlyStudyBuddyProFlashcards {


    /**
     * Returns an unmodifiable view of the flashcards list.
     * This list will not contain any duplicate flashcards.
     */
    ObservableList<Flashcard> getFlashcardList();

    /**
     * Returns an unmodifiable view of the tag list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();
}
