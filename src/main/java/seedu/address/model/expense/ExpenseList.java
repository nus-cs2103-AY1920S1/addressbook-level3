package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.expense.exceptions.DuplicateExpenseException;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.model.expense.exceptions.ExpenseNotRemovableException;

/**
 * List containing {@code Expense}s.
 */
public class ExpenseList implements Iterable<Expense> {

    public final ObservableList<Expense> internalList = FXCollections.observableArrayList();
    public final ObservableList<Expense> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent expense as the given argument.
     */
    public boolean contains(Expense toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameExpense);
    }

    /**
     * Adds an expense to the list.
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
     * Sorts the expenses in the list by comparing the property of expenses.
     */
    public void sort(String property) {
        assert property.equals("name") || property.equals("amount");

        List<Expense> sortedList = new ArrayList<>(List.copyOf(internalList));
        if (property.equals("name")) {
            sortedList.sort(new LexicographicComparator());
        } else {
            sortedList.sort(new ValueComparator());
        }

        if (sortedList.equals(internalList)) {
            internalList.setAll(sortedList);
            Collections.reverse(internalList);
        } else {
            internalList.setAll(sortedList);
        }
    }

    /**
     * Get the largest day number in a list of expenses.
     */
    public int getLargestDayNumber() {
        List<Expense> copiedList = new ArrayList<>(List.copyOf(internalList));
        copiedList.sort(Comparator.comparingInt(e -> e.getDayNumber().getValue()));
        return copiedList.get(copiedList.size() - 1).getDayNumber().getValue();
    }



    /**
     * Replaces the expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the list.
     * The expense identity of {@code editedExpense} must not be the same as another in the list.
     */
    public void set(Expense target, Expense edited) throws ExpenseNotFoundException {
        requireAllNonNull(target, edited);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ExpenseNotFoundException();
        }
        if (!target.isSameExpense(edited) && contains(edited)) {
            throw new DuplicateExpenseException();
        }
        internalList.set(index, edited);
    }

    public void set(List<Expense> occurrences) {
        requireAllNonNull(occurrences);
        if (!expensesAreUnique(occurrences)) {
            throw new DuplicateExpenseException();
        }
        internalList.setAll(occurrences);
    }

    /**
     * Removes the equivalent {@code Expense} from the list.
     * The {@code Expense} must exist in the list.
     */
    public void remove(Expense toRemove) throws ExpenseNotFoundException {
        requireNonNull(toRemove);
        List<Expense> events = new ArrayList<>();
        if (!internalList.stream().anyMatch(toRemove:: isSameExpense)) {
            throw new ExpenseNotFoundException();
        }
        internalList.stream().filter(toRemove:: isSameExpense).forEach(i -> events.add(i));
        internalList.remove(events.get(0));
    }

    /**
     * Removes the {@code Expense} at the specified index.
     *
     * @param index The index of the {@code Expense} to remove.
     */
    public void remove(Index index) {
        requireNonNull(index);
        internalList.remove(index.getZeroBased());
    }

    /**
     * Removes the equivalent {@code Expense} from the list by user command.
     * The {@code Expense} must exist in the list.
     */
    public void removeByUser(Expense toRemove) throws ExpenseNotFoundException,
            ExpenseNotRemovableException {
        requireNonNull(toRemove);
        if (toRemove instanceof MiscExpense) {
            if (!internalList.remove(toRemove)) {
                throw new ExpenseNotFoundException();
            }
        } else {
            throw new ExpenseNotRemovableException();
        }
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
     * Returns true if the list contains only unique {@code Expense}s.
     */
    private boolean expensesAreUnique(List<Expense> occurrence) {
        for (int i = 0; i < occurrence.size() - 1; i++) {
            for (int j = i + 1; j < occurrence.size(); j++) {
                if (occurrence.get(i).isSameExpense(occurrence.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Comparator class that compares two expenses according to the lexicographic order of their names
     */
    static class LexicographicComparator implements Comparator<Expense> {
        @Override
        public int compare(Expense a, Expense b) {
            return a.getName().toString().compareToIgnoreCase(b.getName().toString());
        }
    }

    /**
     * Comparator class that compares two expenses according to the amount of the expenses
     */
    static class ValueComparator implements Comparator<Expense> {
        @Override
        public int compare(Expense a, Expense b) {
            return Double.compare(a.getBudget().getValue(), b.getBudget().getValue());
        }
    }
}
