package mams.logic.history;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of CommandHistory that also allows for filtering of the
 * internally stored {@code FilteredList}. Used defensively for passing to other
 * objects who are permitted to manipulate the view, but not the state (eg. Commands)
 */
public interface FilterOnlyCommandHistory extends ReadOnlyCommandHistory {

    /**
     * Returns an unmodifiable filtered view of {@code inputOutputHistory}.
     * @return unmodifiable ObservableList
     */
    public ObservableList<InputOutput> getFilteredCommandHistory();

    public void updateFilteredCommandHistory(Predicate<InputOutput> predicate);
}
