package seedu.algobase.model.commandhistory;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of {@code CommandHistory}.
 *
 * Supports a minimal set of list operations.
 */
public class CommandHistoryList implements Iterable<CommandHistory> {

    private final ObservableList<CommandHistory> internalList = FXCollections.observableArrayList();
    private final ObservableList<CommandHistory> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a {@code CommandHistory} to the list.
     */
    public void add(CommandHistory toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CommandHistory> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<CommandHistory> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CommandHistoryList // instanceof handles nulls
            && internalList.equals(((CommandHistoryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
