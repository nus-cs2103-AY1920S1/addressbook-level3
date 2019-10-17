package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;
import seedu.address.model.spending.Spending;
import seedu.address.model.spending.UniqueSpendingList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

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

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setSpendings(newData.getSpendingList());
        setBudget(newData.getBudget());
    }

    //// Spending-level operations

    /**
     * Returns true if a Spending with the same identity as {@code Spending} exists in the address book.
     */
    public boolean hasSpending(Spending spending) {
        requireNonNull(spending);
        return spendings.contains(spending);
    }

    /**
     * Adds a Spending to the address book.
     * The Spending must not already exist in the address book.
     */
    public void addSpending(Spending p) {
        spendings.add(p);
    }

    /**
     * Replaces the given Spending {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The Spending identity of {@code editedPerson} must not be the same as another existing
     * Spending in the address book.
     */
    public void setSpending(Spending target, Spending editedSpending) {
        requireNonNull(editedSpending);

        spendings.setSpending(target, editedSpending);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
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
     * Replaces the value of budget in the address book with {@code budget}.
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
                || (other instanceof AddressBook // instanceof handles nulls
                && spendings.equals(((AddressBook) other).spendings)
                && budget.equals(((AddressBook) other).budget));
    }

    @Override
    public int hashCode() {
        return Objects.hash(spendings, budget);
    }
}
