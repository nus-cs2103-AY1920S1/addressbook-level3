package seedu.weme.statistics;

import javafx.beans.Observable;
import javafx.collections.ObservableMap;

/**
 * An unmodifiable view of like data.
 */
public interface ReadOnlyLikeData extends Observable {

    /**
     * Returns an unmodifiable view of the like data.
     */
    ObservableMap<String, Integer> getLikeData();
}
