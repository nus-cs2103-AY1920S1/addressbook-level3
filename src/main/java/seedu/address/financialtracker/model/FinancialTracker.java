package seedu.address.financialtracker.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.logic.commands.exceptions.CommandException;

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

    public void setExpense(Expense expenseToEdit, Expense editedExpense) throws CommandException {
        requireNonNull(editedExpense);
        expenseList.setExpense(expenseToEdit, editedExpense);
    }
}
