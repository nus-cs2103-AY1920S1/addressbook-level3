package seedu.address.model.spending;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.spending.exceptions.DuplicateSpendingException;
import seedu.address.model.spending.exceptions.SpendingNotFoundException;

/**
 * A list of spendings that enforces uniqueness between its elements and does not allow nulls.
 * A Spending is considered unique by comparing using {@code Spending#isSameSpending(Spending)}.
 * As such, adding and updating of spending uses Spending#isSameSpending(Spending) for equality so as to ensure that the
 * Spending being added or updated is unique in terms of identity in the UniqueSpendingList.
 * However, the removal of a Spending uses Spending#equals(Object) so
 * as to ensure that the Spending with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Spending#isSameSpending(Spending)
 */
public class UniqueSpendingList implements Iterable<Spending> {

    private final ObservableList<Spending> internalList = FXCollections.observableArrayList();
    private final ObservableList<Spending> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Spending as the given argument.
     */
    public boolean contains(Spending toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameSpending);
    }

    /**
     * Adds a Spending to the list.
     * The Spending must not already exist in the list.
     */
    public void add(Spending toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSpendingException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Spending {@code target} in the list with {@code editedSpending}.
     * {@code target} must exist in the list.
     * The Spending identity of {@code editedSpending} must not be the same as another existing Spending in the list.
     */
    public void setSpending(Spending target, Spending editedSpending) {
        requireAllNonNull(target, editedSpending);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new SpendingNotFoundException();
        }

        if (!target.isSameSpending(editedSpending) && contains(editedSpending)) {
            throw new DuplicateSpendingException();
        }

        internalList.set(index, editedSpending);
    }

    /**
     * Removes the equivalent Spending from the list.
     * The Spending must exist in the list.
     */
    public void remove(Spending toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new SpendingNotFoundException();
        }
    }

    public void setSpendings(UniqueSpendingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code spendings}.
     * {@code spendings} must not contain duplicate spendings.
     */
    public void setSpendings(List<Spending> spendings) {
        requireAllNonNull(spendings);
        if (!spendingsAreUnique(spendings)) {
            throw new DuplicateSpendingException();
        }

        internalList.setAll(spendings);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Spending> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Spending> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueSpendingList // instanceof handles nulls
                        && internalList.equals(((UniqueSpendingList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code spendings} contains only unique spendings.
     */
    private boolean spendingsAreUnique(List<Spending> spendings) {
        for (int i = 0; i < spendings.size() - 1; i++) {
            for (int j = i + 1; j < spendings.size(); j++) {
                if (spendings.get(i).isSameSpending(spendings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
