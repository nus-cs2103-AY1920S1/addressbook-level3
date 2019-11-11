package seedu.ichifund.model.analytics;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of data objects that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class DataList implements Iterable<Data> {

    private final ObservableList<Data> internalList = FXCollections.observableArrayList();
    private final ObservableList<Data> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a data object to the list.
     */
    public void add(Data toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setData(DataList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     */
    public void setData(List<Data> replacement) {
        requireAllNonNull(replacement);
        internalList.setAll(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Data> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Data> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DataList // instanceof handles nulls
                && internalList.equals(((DataList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
