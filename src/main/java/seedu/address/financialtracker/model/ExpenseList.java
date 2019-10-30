package seedu.address.financialtracker.model;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * An expense list for financial expenses.
 */
public class ExpenseList {

    private final String country;
    private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
    private final ObservableList<Expense> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(expenses);

    public ExpenseList(String country) {
        this.country = country;
    }

    private void sort() {
        Collections.sort(expenses, (ep1, ep2) -> ep2.getTime().valueToCompare - ep1.getTime().valueToCompare);
        Collections.sort(expenses, (ep1, ep2) -> ep2.getDate().valueToCompare.compareTo(ep1.getDate().valueToCompare));
    }

    /**
     * Adds an expense into this representative expense list.
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
        sort();
    }

    /**
     * Deletes an expense with index zero-based.
     * @param index One-based index stating which index or the expense to delete.
     */
    public void deleteExpense(int index) {
        expenses.remove(index - 1);
        sort();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Expense> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public boolean contains(Expense toCheck) {
        requireNonNull(toCheck);
        return expenses.stream().anyMatch(toCheck::equals);
    }

    public void setExpense(Expense expenseToEdit, Expense editedExpense) throws CommandException {
        CollectionUtil.requireAllNonNull(expenseToEdit, editedExpense);

        int index = expenses.indexOf(expenseToEdit);
        if (index == -1) {
            throw new CommandException("The index that you gave is not valid.");
        }

        expenses.set(index, editedExpense);
        sort();
    }

    public boolean isEmpty() {
        return expenses.isEmpty();
    }

    public double getSummary() {
        double sum = 0;
        for(Expense expense : expenses) {
            sum += expense.getAmount().numericalValue;
        }
        return sum;
    }
}
