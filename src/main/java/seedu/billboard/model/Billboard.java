package seedu.billboard.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.ExpenseList;

/**
 * Wraps all data at the Billboard level
 * Duplicates are allowed
 */
public class Billboard implements ReadOnlyBillboard {

    private final ExpenseList expenses;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        expenses = new ExpenseList();
    }

    public Billboard() {}

    /**
     * Creates an Billboard using the Persons in the {@code toBeCopied}
     */
    public Billboard(ReadOnlyBillboard toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the expense list with {@code expense}.
     * {@code expense} must not contain duplicate expense.
     */
    public void setExpenses(List<Expense> expense) {
        this.expenses.setExpenses(expense);
    }

    /**
     * Resets the existing data of this {@code Billboard} with {@code newData}.
     */
    public void resetData(ReadOnlyBillboard newData) {
        requireNonNull(newData);

        setExpenses(newData.getExpenses());
    }

    //// expense-level operations

    /**
     * Returns true if a expense with the same identity as {@code expense} exists in the address book.
     */
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return expenses.contains(expense);
    }

    /**
     * Adds an expense to the address book.
     * The expense must not already exist in the address book.
     */
    public void addExpense(Expense p) {
        expenses.add(p);
    }

    /**
     * Replaces the given expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the address book.
     * The expense identity of {@code editedExpense} must not be the same as another
     * existing expense in the address book.
     */
    public void setExpense(Expense target, Expense editedExpense) {
        requireNonNull(editedExpense);

        expenses.setExpense(target, editedExpense);
    }

    /**
     * Removes {@code key} from this {@code Billboard}.
     * {@code key} must exist in the address book.
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
    public ObservableList<Expense> getExpenses() {
        return expenses.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Billboard // instanceof handles nulls
                && expenses.equals(((Billboard) other).expenses));
    }

    @Override
    public int hashCode() {
        return expenses.hashCode();
    }
}
