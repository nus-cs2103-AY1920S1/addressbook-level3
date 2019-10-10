package seedu.billboard.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.billboard.model.person.Expense;
import seedu.billboard.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameExpense comparison)
 */
public class Billboard implements ReadOnlyBillboard {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
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
     * Replaces the contents of the expense list with {@code expenses}.
     * {@code expenses} must not contain duplicate expenses.
     */
    public void setPersons(List<Expense> expenses) {
        this.persons.setPersons(expenses);
    }

    /**
     * Resets the existing data of this {@code Billboard} with {@code newData}.
     */
    public void resetData(ReadOnlyBillboard newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// expense-level operations

    /**
     * Returns true if a expense with the same identity as {@code expense} exists in the address book.
     */
    public boolean hasPerson(Expense expense) {
        requireNonNull(expense);
        return persons.contains(expense);
    }

    /**
     * Adds a expense to the address book.
     * The expense must not already exist in the address book.
     */
    public void addPerson(Expense p) {
        persons.add(p);
    }

    /**
     * Replaces the given expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the address book.
     * The expense identity of {@code editedExpense} must not be the same as another existing expense in the address book.
     */
    public void setPerson(Expense target, Expense editedExpense) {
        requireNonNull(editedExpense);

        persons.setPerson(target, editedExpense);
    }

    /**
     * Removes {@code key} from this {@code Billboard}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Expense key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Expense> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Billboard // instanceof handles nulls
                && persons.equals(((Billboard) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
