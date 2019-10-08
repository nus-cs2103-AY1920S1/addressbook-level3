package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.income.Income;
import seedu.address.model.income.UniqueIncomeList;

/**
 * Wraps all data at the income-book level
 * Duplicates are not allowed (by .isSameIncome comparison)
 */
public class IncomeBook implements ReadOnlyIncomeBook {

    private final UniqueIncomeList incomes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        incomes = new UniqueIncomeList();
    }

    public IncomeBook() {}

    /**
     * Creates an IncomeBook using the incomes in the {@code toBeCopied}
     */
    public IncomeBook(ReadOnlyIncomeBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the claims list with {@code claims}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setIncomes(List<Income> listOfIncome) {
        this.incomes.setIncomes(listOfIncome);
    }

    /**
     * Replaces the given claim {@code target} in the list with {@code editedClaim}.
     * {@code target} must exist in the address book.
     * The claim identity of {@code editedClaim} must not be the same as another existing claim in the address book.
     */
    public void setIncomes(Income target, Income editedIncome) {
        requireNonNull(editedIncome);

        incomes.setIncome(target, editedIncome);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyIncomeBook newData) {
        requireNonNull(newData);

        setIncomes(newData.getIncomeList());
    }

    //// income-level operations

    /**
     * Returns true if an income with the same content as {@code income} exists in the income book.
     */
    public boolean hasIncome(Income income) {
        requireNonNull(income);
        return incomes.contains(income);
    }

    /**
     * Adds an income to the income book.
     * The income must not already exist in the book
     */
    public void addIncome(Income income) {
        incomes.add(income);
    }


    /**
     * Removes {@code key} from this {@code incomeBook}.
     * {@code key} must exist in the income book.
     */
    public void removeIncome(Income key) {
        incomes.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return incomes.asUnmodifiableObservableList().size() + " incomes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Income> getIncomeList() {
        return incomes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IncomeBook // instanceof handles nulls
                && incomes.equals(((IncomeBook) other).incomes));
    }

    @Override
    public int hashCode() {
        return incomes.hashCode();
    }

}
