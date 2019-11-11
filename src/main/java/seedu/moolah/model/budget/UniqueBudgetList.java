package seedu.moolah.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moolah.model.budget.exceptions.BudgetNotFoundException;
import seedu.moolah.model.budget.exceptions.DeleteDefaultBudgetException;
import seedu.moolah.model.budget.exceptions.DuplicateBudgetException;
import seedu.moolah.model.budget.exceptions.SwitchToFuturePeriodException;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.general.Description;
import seedu.moolah.model.general.Timestamp;

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
     * Checks whether the list contains an equivalent budget as the given argument.
     *
     * @param toCheck The budget to check.
     * @return True if the list contains budget identical to the budget to check.
     */
    public boolean contains(Budget toCheck) {
        requireNonNull(toCheck);
        return internalList
                .stream()
                .anyMatch(toCheck::isSameBudget);
    }

    /**
     * Adds a budget to the list, and sets it to primary.
     * The budget must not already exist in the list.
     *
     * @param toAdd The budget to be added to the list.
     */
    public void add(Budget toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBudgetException();
        }

        Budget normalized = toAdd.normalize(Timestamp.getCurrentTimestamp());

        internalList.add(normalized);
        setPrimary(normalized);
    }

    /**
     * Adds a budget to the list when reconstructing MooLah from json storage file.
     * The primary status of budget is maintained.
     *
     * @param toAdd The budget to be added to the list.
     */
    public void addBudgetFromStorage(Budget toAdd) {
        requireNonNull(toAdd);
        if (toAdd.isDefaultBudget()) {
            internalList.remove(getDefaultBudget());
            internalList.add(toAdd);
            return;
        }
        if (contains(toAdd)) {
            throw new DuplicateBudgetException();
        }
        Budget normalized = toAdd.normalize(Timestamp.getCurrentTimestamp());
        internalList.add(normalized);
    }

    /**
     * Sets the budgets in the list from an existing UniqueBudgetList.
     *
     * @param replacement The given UniqueBudgetList.
     */
    public void setBudgets(UniqueBudgetList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Sets the budgets in the list from an existing List of budgets.
     *
     * @param budgets The given List of budgets.
     * @throws DuplicateBudgetException If the given list contains duplicate budgets.
     */
    public void setBudgets(List<Budget> budgets) {
        requireNonNull(budgets);
        if (!budgetsAreUnique(budgets)) {
            throw new DuplicateBudgetException();
        }

        internalList.setAll(budgets);
    }

    /**
     * Removes an expense from its corresponding budget.
     *
     * @param key The expense to be removed.
     */
    public void removeExpense(Expense key) {
        requireNonNull(key);
        Budget budget = getBudgetWithName(key.getBudgetName());
        Budget copy = budget.deepCopy();
        copy.removeExpense(key);
        setBudget(budget, copy);
    }

    /**
     * Replaces an expense with updated version in its corresponding budget.
     *
     * @param target The expense to be replaced.
     * @param edited The updated expense.
     */
    public void setExpense(Expense target, Expense edited) {
        requireAllNonNull(target, edited);
        Budget budget = getBudgetWithName(target.getBudgetName());
        Budget copy = budget.deepCopy();
        copy.setExpense(target, edited);
        setBudget(budget, copy);
    }

    /**
     * Sets the specified budget to primary. Sets all other budgets in the list to non-primary at the same time.
     *
     * @param budget The budget to be set to primary budget.
     */
    public void setPrimary(Budget budget) {
        requireNonNull(budget);
        for (Budget b : internalList) {
            if (b.isPrimary() && !b.isSameBudget(budget)) {
                Budget copy = b.deepCopy();
                copy.setToNotPrimary();
                setBudget(b, copy);
            }
        }
        Budget copy = budget.deepCopy();
        copy.setToPrimary();
        setBudget(budget, copy);
    }

    /**
     * Sets the budget with the specified description to primary.
     *
     * @param s A string representing the description of the budget to be set to primary.
     */
    public void setPrimaryFromString(String s) {
        requireNonNull(s);
        Description name = new Description(s);
        if (!hasBudgetWithName(name)) {
            throw new BudgetNotFoundException();
        }
        setPrimary(getBudgetWithName(name));
    }

    /**
     * Returns the primary budget in this list.
     *
     * @return The primary budget in this list.
     */
    public Budget getPrimaryBudget() {
        Budget primaryBudget = null;
        for (int i = 0; i < internalList.size(); i++) {
            Budget b = internalList.get(i);
            if (b.isPrimary()) {
                primaryBudget = b;
                break;
            }
        }
        return primaryBudget;
    }

    /**
     * Changes window of primary budget to a different window anchored by the specified date.
     * Not allowed to switch to a future period.
     *
     * @param date A date to anchor the window to be switched to.
     */
    public void changePrimaryBudgetWindow(Timestamp date) {
        requireNonNull(date);
        Budget primaryBudget = getPrimaryBudget();
        Budget currentPeriod = primaryBudget.deepCopy().normalize(Timestamp.getCurrentTimestamp());

        if (date.dateIsAfter(currentPeriod.getWindowEndDate())) {
            throw new SwitchToFuturePeriodException();
        }

        Budget copy = primaryBudget.deepCopy().normalize(date);
        setBudget(primaryBudget, copy);
    }

    /**
     * Returns true if there is a budget with the specified description in the list.
     *
     * @param targetDescription The specified description.
     * @return True if there exists a budget with the specified description in the list, false otherwise.
     */
    public boolean hasBudgetWithName(Description targetDescription) {
        requireNonNull(targetDescription);
        return internalList
                .stream()
                .anyMatch(b -> b.getDescription().equals(targetDescription));
    }

    /**
     * Returns a budget with the specified description in the list.
     *
     * @param targetDescription The specified description.
     * @return The budget with the specified description in the list.
     */
    public Budget getBudgetWithName(Description targetDescription) {
        requireNonNull(targetDescription);
        Budget targetBudget = null;
        for (int i = 0; i < internalList.size(); i++) {
            Budget b = internalList.get(i);
            if (b.getDescription().equals(targetDescription)) {
                targetBudget = b;
                break;
            }
        }
        return targetBudget;
    }

    /**
     * Returns the default budget in this list.
     *
     * @return The budget with the same description as default budget.
     */
    private Budget getDefaultBudget() {
        return getBudgetWithName(Budget.DEFAULT_BUDGET_DESCRIPTION);
    }

    /**
     * Removes a budget from this unique budget list. Default budget cannot be removed.
     * Expenses from the removed budget are transferred to the default budget.
     * If the removed budget was primary, default budget is set to primary.
     *
     * @param toRemove The budget to be removed.
     */
    public void remove(Budget toRemove) {
        requireNonNull(toRemove);
        if (toRemove.isDefaultBudget()) {
            throw new DeleteDefaultBudgetException();
        }
        if (!internalList.remove(toRemove)) {
            throw new BudgetNotFoundException();
        }
        Budget defaultBudget = this.getDefaultBudget().deepCopy();
        toRemove.transferExpensesTo(defaultBudget);
        setBudget(getDefaultBudget(), defaultBudget);

        if (toRemove.isPrimary()) {
            setPrimary(defaultBudget);
        }
    }

    /**
     * Deletes the budget with the specified description.
     *
     * @param description The specified description.
     */
    public void deleteBudgetWithName(Description description) {
        requireNonNull(description);
        Budget toRemove = getBudgetWithName(description);
        remove(toRemove);
    }

    /**
     * Clears all budgets in the list, except the default budget.
     * Expenses are transferred to the default budget.
     * Default budget is set to primary after this process.
     */
    public void clearBudgets() {
        Budget copy = getDefaultBudget().deepCopy();
        for (Budget b : internalList) {
            if (!b.isDefaultBudget()) {
                b.transferExpensesTo(copy);
            }
        }
        internalList.clear();
        Budget copy2 = copy.deepCopy();
        copy2.setToPrimary();
        internalList.add(copy2);
    }

    /**
     * Switches primary budget to the budget with the specified description.
     *
     * @param targetDescription The specified description.
     */
    public void switchBudgetTo(Description targetDescription) {
        requireNonNull(targetDescription);
        Budget targetBudget = getBudgetWithName(targetDescription);
        setPrimary(targetBudget);
    }

    /**
     * Replaces the target budget with an edited budget.
     *
     * @param target The budget to be replaced.
     * @param editedBudget The updated budget.
     */
    public void setBudget(Budget target, Budget editedBudget) {
        requireAllNonNull(target, editedBudget);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BudgetNotFoundException();
        }
        if (!target.isSameBudget(editedBudget) && contains(editedBudget)) {
            throw new DuplicateBudgetException();
        }
        target.transferExpensesTo(editedBudget);
        internalList.set(index, editedBudget.deepCopy());
    }

    /**
     * Checks if this UniqueBudgetList is empty.
     *
     * @return True if there are no budgets, false otherwise.
     */
    public boolean isEmpty() {
        return internalList.size() == 0;
    }

    public ObservableList<Budget> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Budget> iterator() {
        return internalList.iterator();
    }

    /**
     * Checks whether another object is identical to this UniqueBudgetList.
     *
     * @param other The other object to be compared.
     * @return True if the other object is a UniqueBudgetList with the same internalList, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBudgetList // instanceof handles nulls
                && internalList.equals(((UniqueBudgetList) other).internalList));
    }


    /**
     * Creates a hash code by hashing the internalList.
     *
     * @return A hash code of this UniqueBudgetList object.
     */
    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code budgets} contains only unique budgets.
     *
     * @param budgets The List of budget to be inspected.
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
