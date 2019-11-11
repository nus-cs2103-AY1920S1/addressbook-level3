package seedu.guilttrip.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.guilttrip.model.entry.exceptions.EntryNotFoundException;

/**
 * A list of wishes that enforces uniqueness between its elements and does not allow nulls.

 * Supports a minimal set of list operations.
 *
 */
public class WishList implements Iterable<Wish> {

    private final ObservableList<Wish> internalList = FXCollections.observableArrayList();
    private final ObservableList<Wish> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent wish as the given argument.
     */
    public boolean contains(Wish toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEntry);
    }

    /**
     * Adds a wish to the list.
     * The wish must not already exist in the list.
     */
    public void add(Wish toAdd) {
        requireNonNull(toAdd);

        internalList.add(toAdd);
    }

    /**
     * Replaces the wish {@code target} in the list with {@code editedWish}.
     * {@code target} must exist in the list.
     * The wish identity of {@code editedWish} must not be the same as another existing wish in the list.
     */
    public void setWish(Wish target, Wish editedWish) {
        requireAllNonNull(target, editedWish);
        int index = internalList.indexOf(target);
        internalList.set(index, editedWish);
    }

    /**
     * Removes the equivalent wish from the list.
     * The wish must exist in the list.
     */
    public void remove(Wish toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EntryNotFoundException();
        }
    }

    public void setEntries(WishList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code wishes}.
     * {@code wishes} must not contain duplicate wishes.
     */
    public void setEntries(List<Wish> wishes) {
        requireAllNonNull(wishes);

        internalList.setAll(wishes);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Wish> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Wish> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WishList // instanceof handles nulls
                && internalList.equals(((WishList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
