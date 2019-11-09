package mams.logic.history;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of CommandHistory. Used mainly for Json loading / saving.
 */
public interface ReadOnlyCommandHistory {

    /**
     * Returns an unmodifiable view of command input and output history.
     */
    public ObservableList<InputOutput> getInputOutputHistory();

    /**
     * Counts number of unsuccessful commands in history.
     * @return int count of unsuccessful commands.
     */
    public int getNumberOfUnsuccessfulCommands();
}
