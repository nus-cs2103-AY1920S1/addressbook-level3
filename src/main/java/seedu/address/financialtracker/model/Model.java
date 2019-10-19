package seedu.address.financialtracker.model;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.financialtracker.model.expense.Expense;

/**
 * Represents the in-memory addressBookModel of the financial tracker data.
 */
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

}
