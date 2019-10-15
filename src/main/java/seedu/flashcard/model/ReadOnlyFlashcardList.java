package seedu.flashcard.model;

import javafx.collections.ObservableList;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * An unmodifiable flashcard list.
 * This is one of the communication window of the model package to the outside.
 */
public interface ReadOnlyFlashcardList {

    /**
     * Get an unmodifiable view of the flashcard list.
     * This list guarantees no duplicate flashcard will be contained.
     */
    ObservableList<Flashcard> getFlashcardList();
}
