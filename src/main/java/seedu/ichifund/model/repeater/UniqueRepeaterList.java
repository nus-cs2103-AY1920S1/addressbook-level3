package seedu.ichifund.model.repeater;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ichifund.model.repeater.exceptions.DuplicateRepeaterException;
import seedu.ichifund.model.repeater.exceptions.RepeaterNotFoundException;

/**
 * A list of repeaters that enforces uniqueness between its elements and does not allow nulls.
 * A repeater is considered unique by comparing using {@code Repeater#isSameRepeater(Repeater)}. As such, adding and
 * updating of repeaters uses Repeater#isSameRepeater(Repeater) for equality so as to ensure that the repeater being
 * added or updated is unique in terms of identity in the UniqueRepeaterList. However, the removal of a repeater uses
 * Repeater#equals(Object) so as to ensure that the repeater with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Repeater#isSameRepeater(Repeater)
 */
public class UniqueRepeaterList implements Iterable<Repeater> {

    private final ObservableList<Repeater> internalList = FXCollections.observableArrayList();
    private final ObservableList<Repeater> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent repeater as the given argument.
     */
    public boolean contains(Repeater toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRepeater);
    }

    /**
     * Adds a repeater to the list.
     * The repeater must not already exist in the list.
     */
    public void add(Repeater toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRepeaterException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the repeater {@code target} in the list with {@code editedRepeater}.
     * {@code target} must exist in the list.
     * The repeater identity of {@code editedRepeater} must not be the same as another existing repeater in the list.
     */
    public void setRepeater(Repeater target, Repeater editedRepeater) {
        requireAllNonNull(target, editedRepeater);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RepeaterNotFoundException();
        }

        if (!target.isSameRepeater(editedRepeater) && contains(editedRepeater)) {
            throw new DuplicateRepeaterException();
        }

        internalList.set(index, editedRepeater);
    }

    /**
     * Removes the equivalent repeater from the list.
     * The repeater must exist in the list.
     */
    public void remove(Repeater toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RepeaterNotFoundException();
        }
    }

    public void setRepeaters(UniqueRepeaterList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code repeaters}.
     * {@code repeaters} must not contain duplicate repeaters.
     */
    public void setRepeaters(List<Repeater> repeaters) {
        requireAllNonNull(repeaters);
        if (!repeatersAreUnique(repeaters)) {
            throw new DuplicateRepeaterException();
        }

        internalList.setAll(repeaters);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Repeater> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Repeater> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRepeaterList // instanceof handles nulls
                        && internalList.equals(((UniqueRepeaterList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code repeaters} contains only unique repeaters.
     */
    private boolean repeatersAreUnique(List<Repeater> repeaters) {
        for (int i = 0; i < repeaters.size() - 1; i++) {
            for (int j = i + 1; j < repeaters.size(); j++) {
                if (repeaters.get(i).isSameRepeater(repeaters.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
