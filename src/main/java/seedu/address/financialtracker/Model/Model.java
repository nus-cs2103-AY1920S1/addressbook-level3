package seedu.address.financialtracker.Model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.financialtracker.Model.Expense.Expense;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class Model {

    private FinancialTracker financialTracker;
    private final FilteredList<Expense> filteredExpenses;

    public Model() {
        this.financialTracker = new FinancialTracker();
        filteredExpenses = new FilteredList<>(this.financialTracker.getExpenseList());
    }

    public void addExpense(Expense expense) {
        this.financialTracker.addExpense(expense);
    }

    public void deleteExpense(int index) {
        this.financialTracker.deleteExpense(index);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Expense}
     */
    public ObservableList<Expense> getFilteredExpenseList() {
        return filteredExpenses;
    }

    public void updateFilteredExpenseList(Predicate<Expense> predicate) {
        requireNonNull(predicate);
        filteredExpenses.setPredicate(predicate);
    }
}
