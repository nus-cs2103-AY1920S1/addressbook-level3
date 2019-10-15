package seedu.address.model.income;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.income.exceptions.DuplicateIncomeException;
import seedu.address.model.income.exceptions.IncomeNotFoundException;


/**
 * A list of incomes that enforces uniqueness between its elements and does not allow nulls.
 * An income is considered unique by comparing using {@code Income#isSameIncome(Income)}. As such, adding and updating
 * of income uses Income#IsSameIncome(income) for equality so as to ensure that the income being added or updated is
 * unique in terms of identity in the UniqueIncomesList. However, the removal of an income uses Income#equals(Object) so
 * as to ensure that the income with exactly the same fields will be removed.
 *
 *
 * @see Income#isSameIncome(Income)
 * */
public class UniqueIncomesList implements Iterable<Income> {

    private final ObservableList<Income> internalList = FXCollections.observableArrayList();
    private final ObservableList<Income> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent income as the given argument.
     */
    public boolean contains(Income toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameIncome);
    }

    /**
     * Adds an income to the list.
     * The income must not already exist in the list.
     */
    public void add(Income toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateIncomeException();
        }
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
        if (index == -1) {
            throw new IncomeNotFoundException();
        }

        if (!target.isSameIncome(editedIncome) && contains(editedIncome)) {
            throw new DuplicateIncomeException();
        }

        internalList.set(index, editedIncome);
    }

    /**
     * Removes the equivalent income from the list.
     * The income must exist in the list.
     */
    public void remove(Income toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new IncomeNotFoundException();
        }
    }

    public void setIncomes(UniqueIncomesList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code listOfIncome}.
     * {@code claims} must not contain duplicate claims.
     */
    public void setIncomes(List<Income> listOfIncome) {
        requireAllNonNull(listOfIncome);
        if (!incomesAreUnique(listOfIncome)) {
            throw new DuplicateIncomeException();
        }

        internalList.setAll(listOfIncome);
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
                || (other instanceof UniqueIncomesList // instanceof handles nulls
                && internalList.equals(((UniqueIncomesList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code incomes} contains only unique incomes.
     */
    private boolean incomesAreUnique(List<Income> incomes) {
        for (int i = 0; i < incomes.size() - 1; i++) {
            for (int j = i + 1; j < incomes.size(); j++) {
                if (incomes.get(i).isSameIncome(incomes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
