package seedu.moneygowhere.model.spending;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moneygowhere.model.spending.exceptions.SpendingNotFoundException;

/**
 * A list of spendings
 * Supports a minimal set of list operations.
 */
public class SpendingList implements Iterable<Spending> {

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
     */
    public void add(Spending toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Adds bulk Spending to the list.
     */
    public void add(List<Spending> addList) {
        requireAllNonNull(addList);
        internalList.addAll(addList);
    }

    /**
     * Replaces the Spending {@code target} in the list with {@code editedSpending}.
     * {@code target} must exist in the list.
     */
    public void setSpending(Spending target, Spending editedSpending) {
        requireAllNonNull(target, editedSpending);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new SpendingNotFoundException();
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

    public void setSpendings(SpendingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code spendings}.
     */
    public void setSpendings(List<Spending> spendings) {
        requireAllNonNull(spendings);
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
                || (other instanceof SpendingList // instanceof handles nulls
                        && internalList.equals(((SpendingList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
