package seedu.address.financialtracker.model;

import javafx.collections.ObservableList;
import seedu.address.financialtracker.model.expense.Expense;

public class FinancialTracker {

    private final ExpenseList expenseList;

    public FinancialTracker() {
        this.expenseList = new ExpenseList();
    }

    public void addExpense(Expense expense) {
        expenseList.addExpense(expense);
    }

    public void deleteExpense(int index) {
        expenseList.deleteExpense(index);
    }

    public ObservableList<Expense> getExpenseList() {
        return expenseList.asUnmodifiableObservableList();
    }
}
