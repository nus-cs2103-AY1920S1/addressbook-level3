package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.UniqueExpenseList;

/**
 * Wraps all data at the expenseList level
 * Duplicates are not allowed (by .isSameExpense comparison)
 */
public class ExpenseList implements ReadOnlyExpenseList {

    private final UniqueExpenseList expenses;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */ {
        expenses = new UniqueExpenseList();
    }

    public ExpenseList() {
    }

    /**
     * Creates an ExpenseList using the Expenses in the {@code toBeCopied}
     */
    public ExpenseList(ReadOnlyExpenseList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the expense list with {@code expenses}.
     * {@code expenses} must not contain duplicate expenses.
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses.setExpenses(expenses);
    }

    /**
     * Resets the existing data of this {@code ExpenseList} with {@code newData}.
     */
    public void resetData(ReadOnlyExpenseList newData) {
        requireNonNull(newData);

        setExpenses(newData.getExpenseList());
    }

    //// expense-level operations

    /**
     * Returns true if a expense with the same identity as {@code expense} exists in the expense list.
     */
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return expenses.contains(expense);
    }

    /**
     * Adds a expense to the expense list.
     * The expense must not already exist in the expense list.
     */
    public void addExpense(Expense p) {
        expenses.add(p);
    }

    /**
     * Replaces the given expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the expense list.
     * The expense identity of {@code editedExpense} must not be the same as another existing expense in the address
     * book.
     */
    public void setExpense(Expense target, Expense editedExpense) {
        requireNonNull(editedExpense);

        expenses.setExpense(target, editedExpense);
    }

    /**
     * Removes {@code key} from this {@code ExpenseList}.
     * {@code key} must exist in the expense list.
     */
    public void removeExpense(Expense key) {
        expenses.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return expenses.asUnmodifiableObservableList().size() + " expenses";
        // TODO: refine later
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return expenses.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExpenseList // instanceof handles nulls
            && expenses.equals(((ExpenseList) other).expenses));
    }

    @Override
    public int hashCode() {
        return expenses.hashCode();
    }
}
