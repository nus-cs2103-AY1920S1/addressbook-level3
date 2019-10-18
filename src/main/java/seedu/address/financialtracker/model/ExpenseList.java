package seedu.address.financialtracker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.financialtracker.model.expense.Expense;

/**
 * An expense list for financial expenses.
 */
public class ExpenseList {

    private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
    private final ObservableList<Expense> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(expenses);

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void deleteExpense(int index) {
        expenses.remove(index - 1);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Expense> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }
}
