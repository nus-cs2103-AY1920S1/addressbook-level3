package seedu.revision.model.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * To support list operations.
 */
public class StatisticsList implements Iterable<Statistics> {

    private final ObservableList<Statistics> internalList = FXCollections.observableArrayList();
    private final ObservableList<Statistics> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean isEmpty() {
        return internalUnmodifiableList.isEmpty();
    }

    /**
     * Adds a statistics to the list.
     */
    public void add(Statistics toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Resets all statistics on the list with a new list.
     * @param replacement new list
     */
    public void setStatistics(StatisticsList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code statistics}.
     */
    public void setStatistics(List<Statistics> statistics) {
        requireAllNonNull(statistics);
        internalList.setAll(statistics);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Statistics> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Statistics> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatisticsList // instanceof handles nulls
                && internalList.equals(((StatisticsList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
