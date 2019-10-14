package seedu.address.financialtracker.Model;

import seedu.address.financialtracker.Model.Expense.Expense;

public class Model {
    private FinancialTracker financialTracker;

    public Model() {
        this.financialTracker = new FinancialTracker();
    }

    public void addExpense(Expense expense) {
        this.financialTracker.addExpense(expense);
    }

    public void deleteExpense(int index) {
        this.financialTracker.deleteExpense(index);
    }
}
