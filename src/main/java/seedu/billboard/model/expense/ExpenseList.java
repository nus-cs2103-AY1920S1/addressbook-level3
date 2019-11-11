package seedu.billboard.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.billboard.model.expense.exceptions.DuplicateExpenseException;
import seedu.billboard.model.expense.exceptions.ExpenseNotFoundException;

/**
 * A list of expenses. The expenses are unique as specified by the {@code equals} method of the {@code Expense} class
 * Supports a minimal set of list operations.
 *
 */
public class ExpenseList implements Iterable<Expense> {

    private final ObservableList<Expense> internalList = FXCollections.observableArrayList();
    private final ObservableList<Expense> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public ExpenseList() {}

    /**
     * Initialise {@code ExpenseList} from a collection of expenses.
     *
     * @param expenses collection of expenses
     */
    public ExpenseList(Collection<Expense> expenses) {
        requireNonNull(expenses);

        expenses.forEach(e -> internalList.add(e));
    }

    /**
     * Returns true if the list contains an equivalent expense as the given argument.
     */
    public boolean contains(Expense toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Clears all expenses
     */
    public void clear() {
        internalList.clear();
    }

    /**
     * Get the count of expenses in {@code ExpenseList}
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Adds a expense to the list.
     * The expense must not already exist in the list.
     */
    public void add(Expense toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateExpenseException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the record {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the list.
     * The record {@code editedExpense} must not be the same as another existing record in the list.
     */
    public void setExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ExpenseNotFoundException();
        }

        if (!target.equals(editedExpense) && contains(editedExpense)) {
            throw new DuplicateExpenseException();
        }

        internalList.set(index, editedExpense);
    }

    /**
     * Removes the equivalent record from the list.
     * The record must exist in the list.
     */
    public void remove(Expense toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ExpenseNotFoundException();
        }
    }

    public void setExpenses(ExpenseList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code expense}.
     * {@code expense} must not contain duplicate expense.
     */
    public void setExpenses(List<Expense> expense) {
        requireAllNonNull(expense);
        if (!expensesAreUnique(expense)) {
            throw new DuplicateExpenseException();
        }

        internalList.setAll(expense);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Expense> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Expense> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseList // instanceof handles nulls
                        && internalList.equals(((ExpenseList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Expense} contains only unique Expense.
     */
    private boolean expensesAreUnique(List<? extends Expense> expenses) {
        for (int i = 0; i < expenses.size() - 1; i++) {
            for (int j = i + 1; j < expenses.size(); j++) {
                if (expenses.get(i).equals(expenses.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public ExpenseList getClone() {
        ExpenseList clonedList = new ExpenseList();
        for (Expense e : this.internalList) {
            Expense clonedExpense = e.getClone(); // get a deep clone of each expense
            clonedList.add(clonedExpense);
        }
        return clonedList;
    }

    public Expense get(int i) {
        return internalList.get(i);
    }
}
