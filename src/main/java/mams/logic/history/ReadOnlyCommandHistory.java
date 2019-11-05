package mams.logic.history;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of CommandHistory.
 */
public interface ReadOnlyCommandHistory {

    /**
     * Returns an unmodifiable view of command input and output history.
     */
    public ObservableList<InputOutput> getInputOutputHistory();
}
