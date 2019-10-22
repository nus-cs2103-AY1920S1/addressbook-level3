package seedu.address.model.eatery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.eatery.exceptions.DuplicateEateryException;
import seedu.address.model.eatery.exceptions.EateryNotFoundException;

/**
 * A list of eateries that enforces uniqueness between its elements and does not allow nulls.
 * A eatery is considered unique by comparing using {@code Eatery#isSameEatery(Eatery)}. As such, adding and updating of
 * eateries uses Eatery#isSameEatery(Eatery) for equality so as to ensure that the eatery being added or updated is
 * unique in terms of identity in the UniqueEateryList. However, the removal of a eatery uses Eatery#equals(Object) so
 * as to ensure that the eatery with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Eatery#isSameEatery(Eatery)
 */
public class UniqueEateryList implements Iterable<Eatery> {

    private final ObservableList<Eatery> internalList = FXCollections.observableArrayList();
    private final ObservableList<Eatery> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent eatery as the given argument.
     */
    public boolean contains(Eatery toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEatery);
    }

    /**
     * Adds a eatery to the list.
     * The eatery must not already exist in the list.
     */
    public void add(Eatery toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEateryException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the eatery {@code target} in the list with {@code editedEatery}.
     * {@code target} must exist in the list.
     * The eatery identity of {@code editedEatery} must not be the same as another existing eatery in the list.
     */
    public void setEatery(Eatery target, Eatery editedEatery) {
        requireAllNonNull(target, editedEatery);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EateryNotFoundException();
        }

        if (!target.isSameEatery(editedEatery) && contains(editedEatery)) {
            throw new DuplicateEateryException();
        }

        internalList.set(index, editedEatery);
    }

    /**
     * Removes the equivalent eatery from the list.
     * The eatery must exist in the list.
     */
    public void remove(Eatery toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EateryNotFoundException();
        }
    }

    public void setEateries(UniqueEateryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code eateries}.
     * {@code eateries} must not contain duplicate eateries.
     */
    public void setEateries(List<Eatery> eateries) {
        requireAllNonNull(eateries);
        if (!eateriesAreUnique(eateries)) {
            throw new DuplicateEateryException();
        }

        internalList.setAll(eateries);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Eatery> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Eatery> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEateryList // instanceof handles nulls
                        && internalList.equals(((UniqueEateryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code eateries} contains only unique eateries.
     */
    private boolean eateriesAreUnique(List<Eatery> eateries) {
        for (int i = 0; i < eateries.size() - 1; i++) {
            for (int j = i + 1; j < eateries.size(); j++) {
                if (eateries.get(i).isSameEatery(eateries.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
