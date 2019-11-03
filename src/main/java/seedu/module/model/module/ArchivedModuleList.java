package seedu.module.model.module;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of {@code ArchivedModule}. The list is guaranteed not to contain duplicates.
 */
public class ArchivedModuleList implements Iterable<ArchivedModule> {

    private final ObservableList<ArchivedModule> internalList = FXCollections.observableArrayList();
    private final ObservableList<ArchivedModule> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Module as the given argument.
     */
    public boolean contains(ArchivedModule toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds an ArchivedModule to the list.
     */
    public void add(ArchivedModule toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ArchivedModule> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ArchivedModule> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchivedModuleList // instanceof handles nulls
                && internalList.equals(((ArchivedModuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
