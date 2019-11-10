package seedu.address.financialtracker.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

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
    private final Comparator<? super Expense> byTime = (ep1, ep2) -> ep2.getTime().valueToCompare
            - ep1.getTime().valueToCompare;
    private final Comparator<? super Expense> byDate = (ep1, ep2) -> ep2.getDate()
            .getDateToCompare().compareTo(ep1.getDate().getDateToCompare());
    private final Comparator<? super Expense> byAmount = (ep1, ep2) ->
            Double.compare(ep2.getAmount().numericalValue, ep1.getAmount().numericalValue);
    private final Comparator<? super Expense> byType = Comparator.comparing(ep -> ep.getType().value);
    private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
    private final ObservableList<Expense> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(expenses);
    private final String country;
    private final Stack<ExpenseListCopy> undoStack = new Stack<>();
    private Comparator<? super Expense> currentComparator;
    private double maximumCap = 0;

    public ExpenseList(String country) {
        this.country = country;
        this.currentComparator = null;
    }

    /**
     * Sorts this expense list according to current comparator.
     */
    private void sort() {
        if (currentComparator == null) {
            expenses.sort(byTime);
            expenses.sort(byDate);
        } else {
            expenses.sort(currentComparator);
        }
    }

    /**
     * Changes the comparator method specified by user.
     * @param comparator the comparator to compare
     */
    public void setComparator(String comparator) {
        switch(comparator) {
        case "DATE":
            this.currentComparator = byDate;
            break;
        case "TIME":
            this.currentComparator = byTime;
            break;
        case "AMOUNT":
            this.currentComparator = byAmount;
            break;
        case "TYPE":
            this.currentComparator = byType;
            break;
        default:
            this.currentComparator = null;
        }
        sort();
    }

    /**
     * Adds an expense into this representative expense list.
     */
    public void addExpense(Expense expense, boolean isUserInput) throws CommandException {
        if ((maximumCap + expense.getAmount().numericalValue) > 1000000000000d) {
            throw new CommandException("Maximum cap 1 trillion reached! Are you sure you're not crazy?!");
        } else {
            if (isUserInput) {
                undoStack.add(this.copy());
            }
            maximumCap += expense.getAmount().numericalValue;
            expenses.add(expense);
            sort();
        }
    }

    /**
     * Deletes an expense with index zero-based.
     * @param index One-based index stating which index or the expense to delete.
     */
    public void deleteExpense(int index) {
        Expense toDelete = expenses.get(index - 1);
        undoStack.add(this.copy());
        expenses.remove(index - 1);
        maximumCap -= toDelete.getAmount().numericalValue;
        sort();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Expense> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Checks whether this list contains a specific expense.
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
        undoStack.add(this.copy());
        expenses.set(index, editedExpense);
        sort();
    }

    public boolean isEmpty() {
        return expenses.isEmpty();
    }

    /**
     * Returns a total sum of this expense list;
     */
    public double getSummary() {
        double sum = 0;
        for (Expense expense : expenses) {
            sum += expense.getAmount().numericalValue;
        }
        return sum;
    }

    /**
     * CLears this expense list.
     */
    public void clearExpenseList() {
        undoStack.add(this.copy());
        this.expenses.removeAll(expenses);
        this.maximumCap = 0;
    }

    /**
     * Undo and resets the expense list to previous expense list stored in UndoStack.
     * @throws CommandException if the UndoStack is empty
     */
    public void undo() throws CommandException {
        if (this.undoStack.isEmpty()) {
            throw new CommandException("Nothing to undo!");
        } else {
            ExpenseListCopy toUndo = this.undoStack.pop();
            this.expenses.removeAll(expenses);
            this.expenses.addAll(toUndo.expenses);
            this.currentComparator = toUndo.currentComparator;
            this.maximumCap = toUndo.maximumCap;
            sort();
        }
    }

    /**
     * Returns a copy of this expense list.
     */
    private ExpenseListCopy copy() {
        ExpenseListCopy copyExpenseList = new ExpenseListCopy(this.expenses, this.maximumCap, this.currentComparator);
        return copyExpenseList;
    }

    /**
     * A helper class to help storing all the expenses after every actions done by user.
     */
    private class ExpenseListCopy {
        private ArrayList<Expense> expenses = new ArrayList<>();
        private double maximumCap;
        private Comparator<? super Expense> currentComparator;

        public ExpenseListCopy(List<Expense> expenses, double maximumCap,
                               Comparator<? super Expense> currentComparator) {
            for (Expense ep : expenses) {
                this.expenses.add(ep.makeCopy());
            }
            this.maximumCap = maximumCap;
            this.currentComparator = currentComparator;
        }
    }
}
