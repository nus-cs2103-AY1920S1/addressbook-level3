package seedu.guilttrip.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.guilttrip.model.entry.exceptions.EntryNotFoundException;

/**
 * A list of incomes that enforces uniqueness between its elements and does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class IncomeList implements Iterable<Income> {

    private final ObservableList<Income> internalList = FXCollections.observableArrayList();
    private final ObservableList<Income> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent income as the given argument.
     */
    public boolean contains(Income toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEntry);
    }

    /**
     * Adds a income to the list.
     * The income must not already exist in the list.
     */
    public void add(Income toAdd) {
        requireNonNull(toAdd);

        internalList.add(toAdd);
    }

    /**
     * Replaces the income {@code target} in the list with {@code editedIncome}.
     * {@code target} must exist in the list.
     * The income identity of {@code editedIncome} must not be the same as another existing income in the list.
     */
    public void setIncome(Income target, Income editedIncome) {
        requireAllNonNull(target, editedIncome);
        int index = internalList.indexOf(target);
        internalList.set(index, editedIncome);
    }

    /**
     * Removes the equivalent entry from the list.
     * The entry must exist in the list.
     */
    public void remove(Income toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EntryNotFoundException();
        }
    }

    public void setEntries(IncomeList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code incomes}.
     * {@code incomes} must not contain duplicate incomes.
     */
    public void setEntries(List<Income> incomes) {
        requireAllNonNull(incomes);

        internalList.setAll(incomes);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Income> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Income> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IncomeList // instanceof handles nulls
                && internalList.equals(((IncomeList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
