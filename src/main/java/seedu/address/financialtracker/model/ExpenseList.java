package seedu.address.financialtracker.model;

import static java.util.Objects.requireNonNull;

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

    //-----------define sorting types------------//
    private final Comparator<? super Expense> BY_DATE =
            (ep1, ep2) -> ep2.getTime().valueToCompare - ep1.getTime().valueToCompare;
    private final Comparator<? super Expense> BY_TIME = (ep1, ep2) -> ep2.getDate()
            .getDateToCompare().compareTo(ep1.getDate().getDateToCompare());
    private final Comparator<? super Expense> BY_AMOUNT = (ep1, ep2) ->
            Double.compare(ep2.getAmount().numericalValue, ep1.getAmount().numericalValue);

    private final String country;
    private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
    private final ObservableList<Expense> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(expenses);
    private Comparator<? super Expense> currentComparator;

    public ExpenseList(String country) {
        this.country = country;
        this.currentComparator = null;
    }

    /**
     * Sort this expense list according to current comparator.
     */
    private void sort() {
        if (currentComparator == null) {
            expenses.sort(BY_DATE);
            expenses.sort(BY_TIME);
        } else {
            expenses.sort(currentComparator);
        }
    }

    /**
     * Change the comparator method specified by user.
     * @param comparator the comparator to compare
     */
    public void setComparator(String comparator) {
        switch(comparator) {
        case "DATE":
            this.currentComparator = BY_DATE;
            break;
        case "TIME":
            this.currentComparator = BY_TIME;
            break;
        case "AMOUNT":
            this.currentComparator = BY_AMOUNT;
            break;
        case "DEFAULT":
            this.currentComparator = null;
        }
        sort();
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

    /**
     * Check whether this list contains a specific expense.
     * @param toCheck the expense to be checked.
     */
    public boolean contains(Expense toCheck) {
        requireNonNull(toCheck);
        return expenses.stream().anyMatch(toCheck::equals);
    }

    /**
     * Edits an expense.
     * @param expenseToEdit The expense to be edited.
     * @param editedExpense The new expense.
     * @throws CommandException
     */
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
        for (Expense expense : expenses) {
            sum += expense.getAmount().numericalValue;
        }
        return sum;
    }
}
