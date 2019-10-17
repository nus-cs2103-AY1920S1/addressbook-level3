package seedu.address.model.earnings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.earnings.earningsexception.DuplicateEarningsException;
import seedu.address.model.earnings.earningsexception.EarningsNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniqueEarningsList implements Iterable<Earnings> {

    private final ObservableList<Earnings> internalList = FXCollections.observableArrayList();
    private final ObservableList<Earnings> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Earnings toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEarnings);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Earnings toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEarningsException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setEarnings(Earnings target, Earnings editedEarnings) {
        requireAllNonNull(target, editedEarnings);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EarningsNotFoundException();
        }

        if (!target.isSameEarnings(editedEarnings) && contains(editedEarnings)) {
            throw new DuplicateEarningsException();
        }

        internalList.set(index, editedEarnings);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Earnings toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EarningsNotFoundException();
        }
    }

    public void setEarnings(UniqueEarningsList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEarnings(List<Earnings> earnings) {
        requireAllNonNull(earnings);
        if (!earningsAreUnique(earnings)) {
            throw new DuplicateEarningsException();
        }

        internalList.setAll(earnings);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Earnings> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Earnings> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEarningsList // instanceof handles nulls
                && internalList.equals(((UniqueEarningsList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean earningsAreUnique(List<Earnings> earnings) {
        for (int i = 0; i < earnings.size() - 1; i++) {
            for (int j = i + 1; j < earnings.size(); j++) {
                if (earnings.get(i).isSameEarnings(earnings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
