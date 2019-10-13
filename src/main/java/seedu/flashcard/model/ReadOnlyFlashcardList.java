package seedu.flashcard.model;

import javafx.collections.ObservableList;

public interface ReadOnlyFlashcardList {

    /**
     * Get an unmodifiable view of the flashcard list.
     * This list guarantees no duplicate flashcard will be contained.
     */
    ObservableList<Flashcard> getFlashcardList();
}
