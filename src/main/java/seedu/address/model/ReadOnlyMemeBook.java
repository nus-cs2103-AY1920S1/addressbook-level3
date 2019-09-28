package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.meme.Meme;

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
