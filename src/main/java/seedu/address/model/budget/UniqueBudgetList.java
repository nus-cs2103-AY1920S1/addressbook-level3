package seedu.address.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.budget.exceptions.DuplicateBudgetException;

/**
 * A list of budgets that enforces uniqueness between its elements and does not allow nulls.
 * A budget is considered unique by comparing using {@code Budget#isSameBudget(Budget)}.
 * As such, adding and updating of budgets use Budget#isSameBudget(Budget) for equality so
 * as to ensure that the budget being added or updated is unique in terms of identity in
 * the UniqueBudgetList. However, the removal of a budget uses Budget#equals(Object) so
 * as to ensure that the budget with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Budget#isSameBudget(Budget)
 */
public class UniqueBudgetList implements Iterable<Budget> {

    private final ObservableList<Budget> internalList = FXCollections.observableArrayList();
    private final ObservableList<Budget> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent budget as the given argument.
     */
    public boolean contains(Budget toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameBudget);
    }

    /**
     * Adds a budget to the list.
     * The budget must not already exist in the list.
     */
    public void add(Budget toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBudgetException();
        }
        internalList.add(toAdd);
    }

    public void setBudgets(UniqueBudgetList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setBudgets(List<Budget> budgets) {
        requireAllNonNull(budgets);
        if (!budgetsAreUnique(budgets)) {
            throw new DuplicateBudgetException();
        }

        internalList.setAll(budgets);
    }

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
                || (other instanceof UniqueBudgetList // instanceof handles nulls
                && internalList.equals(((UniqueBudgetList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code budgets} contains only unique budgets.
     */
    private boolean budgetsAreUnique(List<Budget> budgets) {
        for (int i = 0; i < budgets.size() - 1; i++) {
            for (int j = i + 1; j < budgets.size(); j++) {
                if (budgets.get(i).isSameBudget(budgets.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
