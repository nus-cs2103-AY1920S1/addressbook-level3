package seedu.system.model;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of some class of data
 */
public interface ReadOnlyData<T> {

    /**
     * Returns an unmodifiable view of the list of data.
     * This list will not contain any duplicate data.
     */
    ObservableList<T> getListOfElements();

}
