package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.flashcard.Flashcard;

/**
 * Interface for ReadOnlyFlashcardBook
 * //to fill up
 */

public interface ReadOnlyFlashcardBook {

    /**
     * Returns an unmodifiable view of the flashcards list.
     * This list will not contain any duplicate flashcards.
     * @return a flashcard list
     */

    ObservableList<Flashcard> getFlashcardList();

}
