package seedu.weme.model;

import javafx.collections.ObservableList;
import seedu.weme.model.meme.Meme;

/**
 * Unmodifiable view of an meme book
 */
public interface ReadOnlyMemeBook {

    /**
     * Returns an unmodifiable view of the memes list.
     * This list will not contain any duplicate memes.
     */
    ObservableList<Meme> getMemeList();

}
