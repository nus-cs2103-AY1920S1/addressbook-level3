package seedu.exercise.model.regime;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.exercise.model.regime.exceptions.DuplicateRegimeException;
import seedu.exercise.model.regime.exceptions.RegimeNotFoundException;

/**
 * A list of regimes that enforces uniqueness between its elements and does not allow nulls.
 * A regime is considered unique by comparing using {@code Regime#isSameRegime(Regime)}.
 * As such, adding and updating of regimes uses Regime#isSameRegime(Regime) for equality so as to ensure that
 * the regime being added or updated is unique in terms of identity in the UniqueRegimeList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Regime#isSameRegime(Regime)
 */
public class UniqueRegimeList implements Iterable<Regime> {

    private final ObservableList<Regime> internalList = FXCollections.observableArrayList();
    private final ObservableList<Regime> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent regime as the given argument.
     */
    public boolean contains(Regime toCheck) {
        requireNonNull(toCheck);
        for (Regime r : internalList) {
            if (r.getRegimeName().equals(toCheck.getRegimeName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an regime to the list.
     * The regime must not already exist in the list.
     */
    public void add(Regime toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRegimeException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the regime {@code target} in the list with {@code editedRegime}.
     * {@code target} must exist in the list.
     * The regime identity of {@code editedRegime} must not be the same as another existing regime in the list.
     */
    public void setRegime(Regime target, Regime editedRegime) {
        requireAllNonNull(target, editedRegime);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RegimeNotFoundException();
        }

        if (!target.isSameRegime(editedRegime) && contains(editedRegime)) {
            throw new DuplicateRegimeException();
        }

        internalList.set(index, editedRegime);
    }

    /**
     * Removes the equivalent regime from the list.
     * The regime must exist in the list.
     */
    public void remove(Regime toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RegimeNotFoundException();
        }
    }

    public void setRegimes(UniqueRegimeList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code regimes}.
     * {@code regimes} must not contain duplicate regimes.
     */
    public void setRegimes(List<Regime> regimes) {
        requireAllNonNull(regimes);
        if (!regimesAreUnique(regimes)) {
            throw new DuplicateRegimeException();
        }

        internalList.setAll(regimes);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Regime> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Regime> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRegimeList // instanceof handles nulls
                && internalList.equals(((UniqueRegimeList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique regimes.
     */
    private boolean regimesAreUnique(List<Regime> regimes) {
        for (int i = 0; i < regimes.size() - 1; i++) {
            for (int j = i + 1; j < regimes.size(); j++) {
                if (regimes.get(i).isSameRegime(regimes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
