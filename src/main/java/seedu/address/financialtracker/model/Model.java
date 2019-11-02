package seedu.address.financialtracker.model;

import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Encapsulates Financial Tracker and it's underlying ObservableMap.
 */
public class Model {

    private FinancialTracker financialTracker;
    private ObservableMap<String, ExpenseList> internalUnmodifiableExpenseListMap;

    public Model() {
        this.financialTracker = new FinancialTracker();
        internalUnmodifiableExpenseListMap = this.financialTracker.getInternalUnmodifiableExpenseListMap();
    }

    /**
     * Adds an expense into the financial tracker.
     */
    public void addExpense(Expense expense) {
        this.financialTracker.addExpense(expense);
    }

    /**
     * Delete an expense from the financial tracker.
     */
    public void deleteExpense(int index) {
        this.financialTracker.deleteExpense(index);
    }

    /**
     * Set the country key in financial tracker.
     */
    public void setCountry(String country) {
        this.financialTracker.setCurrentCountry(country);
    }

    public String getCountry() {
        return this.financialTracker.getCurrentCountry();
    }

    /**
     * Returns an expense list from the underlying Map of financial tracker.
     */
    public ObservableList<Expense> getExpenseList() {
        return internalUnmodifiableExpenseListMap
                .get(financialTracker.getCurrentCountry()).asUnmodifiableObservableList();
    }

    /**
     * Edits an expense from the financial tracker.
     */
    public void setExpense(Expense expenseToEdit, Expense editedExpense) throws CommandException {
        CollectionUtil.requireAllNonNull(expenseToEdit, editedExpense);
        financialTracker.setExpense(expenseToEdit, editedExpense);
    }

    public FinancialTracker getFinancialTracker() {
        return financialTracker;
    }

    //todo: this implementation is so bad. Change it?

    /**
     * Update the Financial Tracker after reading from storage memory.
     */
    public void updateFinancialTracker(FinancialTracker financialTracker) {
        this.financialTracker = financialTracker;
        internalUnmodifiableExpenseListMap = this.financialTracker.getInternalUnmodifiableExpenseListMap();
    }

    public HashMap<String, Double> getSummaryMap() {
        return this.financialTracker.getSummaryMap();
    }
}
