package seedu.moneygowhere.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.spending.UniqueSpendingList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class SpendingBook implements ReadOnlySpendingBook {

    private final UniqueSpendingList spendings;
    private final Budget budget;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        spendings = new UniqueSpendingList();
        budget = new Budget(0);
    }

    public SpendingBook() {}

    /**
     * Creates a SpendingBook using Spending entries in the {@code toBeCopied}
     */
    public SpendingBook(ReadOnlySpendingBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Spending list with {@code spendings}.
     * {@code spendings} must not contain duplicate spendings.
     */
    public void setSpendings(List<Spending> spendings) {
        this.spendings.setSpendings(spendings);
    }

    /**
     * Resets the existing data of this {@code SpendingBook} with {@code newData}.
     */
    public void resetData(ReadOnlySpendingBook newData) {
        requireNonNull(newData);

        setSpendings(newData.getSpendingList());
        setBudget(newData.getBudget());
    }

    //// Spending-level operations

    /**
     * Returns true if a Spending with the same identity as {@code Spending} exists in MoneyGoWhere.
     */
    public boolean hasSpending(Spending spending) {
        requireNonNull(spending);
        return spendings.contains(spending);
    }

    /**
     * Adds a Spending to the MoneyGoWhere list.
     * The Spending must not already exist in the MoneyGoWhere list.
     */
    public void addSpending(Spending p) {
        spendings.add(p);
    }

    /**
     * Replaces the given Spending {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the MoneyGoWhere list.
     * The Spending identity of {@code editedPerson} must not be the same as another existing
     * Spending in the MoneyGoWhere list.
     */
    public void setSpending(Spending target, Spending editedSpending) {
        requireNonNull(editedSpending);

        spendings.setSpending(target, editedSpending);
    }

    /**
     * Removes {@code key} from this {@code SpendingBook}.
     * {@code key} must exist in the MoneyGoWhere list.
     */
    public void removeSpending(Spending key) {
        spendings.remove(key);
    }

    //// Budget related operations

    @Override
    public Budget getBudget() {
        return budget;
    }

    /**
     * Replaces the value of budget in the MoneyGoWhere list with {@code budget}.
     */
    public void setBudget(Budget budget) {
        this.budget.setValue(budget.getValue());
    }

    //// util methods

    @Override
    public String toString() {
        return spendings.asUnmodifiableObservableList().size() + " spendings";
        // TODO: refine later
    }

    @Override
    public ObservableList<Spending> getSpendingList() {
        return spendings.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SpendingBook // instanceof handles nulls
                && spendings.equals(((SpendingBook) other).spendings)
                && budget.equals(((SpendingBook) other).budget));
    }

    @Override
    public int hashCode() {
        return Objects.hash(spendings, budget);
    }
}
