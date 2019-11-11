package seedu.address.model.finance.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.finance.logentry.exceptions.BudgetNotFoundException;

/**
 * A list of budgets that does not allow nulls.
 * The removal of a budget uses Budget#equals(Object) so
 * as to ensure that the budget with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * List contains unique budgets, no duplicates.
 */
public class BudgetList implements Iterable<Budget> {

    private final ObservableList<Budget> internalList = FXCollections.observableArrayList();
    private final ObservableList<Budget> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent log entry as the given argument.
     */
    public boolean contains(Budget toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameBudget);
    }

    /**
     * Adds a budget to the list.
     */
    public void add(Budget toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd)) {
            internalList.add(toAdd);
            sort();
        }
    }

    // Sort budget by start date
    private void sort() {
        FXCollections.sort(internalList, (
                b1, b2) -> b1.getStartDate().compareTo(b2.getStartDate()));
    }

    /**
     * Removes the equivalent log entry from the list.
     * The log entry must exist in the list.
     */
    public void remove(Budget toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BudgetNotFoundException();
        }
    }

    public void setBudgets(BudgetList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of internal list with given list of
     * {@code Budgets}.
     * Sorts Budgets by their start dates.
     * {@code Budgets} must not contain duplicate budgets.
     */
    public void setBudgets(List<Budget> budgets) {
        requireAllNonNull(budgets);
        internalList.setAll(budgets);
        sort();
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Budget> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Budget> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetList // instanceof handles nulls
                        && internalList.equals(((BudgetList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
