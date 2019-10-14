package seedu.address.financialtracker.Model;

import seedu.address.financialtracker.Model.Expense.Expense;

import java.util.ArrayList;

/**
 * An expense list for financial expenses.
 */
public class ExpenseList {

    private ArrayList<Expense> expenses;

    public ExpenseList() {
        this.expenses = new ArrayList<Expense>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void deleteExpense(int index) {
        expenses.remove(index - 1);
    }
}
