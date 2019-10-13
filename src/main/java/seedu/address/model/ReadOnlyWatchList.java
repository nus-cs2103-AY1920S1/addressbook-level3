package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.show.Show;

/**
 * Unmodifiable view of a watchlist
 */
public interface ReadOnlyWatchList {
    /**
     * Returns an unmodifiable view of the watchlist.
     * This list will not contain any duplicate shows.
     */
    ObservableList<Show> getShowList();
}
