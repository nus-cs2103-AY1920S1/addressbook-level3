package seedu.address.financialtracker.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.logic.commands.exceptions.CommandException;

public class Model {

    public static final Predicate<Expense> PREDICATE_SHOW_ALL_EVENTS = unused -> true;
    private FinancialTracker financialTracker;
    private ObservableMap<String, ExpenseList> internalUnmodifiableExpenseListMap;

    public Model() {
        this.financialTracker = new FinancialTracker();
        internalUnmodifiableExpenseListMap = this.financialTracker.getInternalUnmodifiableExpenseListMap();
    }

    public void addExpense(Expense expense) {
        this.financialTracker.addExpense(expense);
    }

    public void deleteExpense(int index) {
        this.financialTracker.deleteExpense(index);
    }

    public void setCountry(String country) {
        this.financialTracker.setCurrentCountry(country);
    }

    public String getCountry() {
        return this.financialTracker.currentCountry;
    }

    public ObservableList<Expense> getExpenseList() {
        return internalUnmodifiableExpenseListMap.get(financialTracker.currentCountry).asUnmodifiableObservableList();
    }

    public void setExpense(Expense expenseToEdit, Expense editedExpense) throws CommandException {
        CollectionUtil.requireAllNonNull(expenseToEdit, editedExpense);
        financialTracker.setExpense(expenseToEdit, editedExpense);
    }

    public FinancialTracker getFinancialTracker() {
        return financialTracker;
    }

    //todo: this implementation is so bad. Change it?
    public void updateFinancialTracker(FinancialTracker financialTracker) {
        this.financialTracker = financialTracker;
        internalUnmodifiableExpenseListMap = this.financialTracker.getInternalUnmodifiableExpenseListMap();
    }
}
