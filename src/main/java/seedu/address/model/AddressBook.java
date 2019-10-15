package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.ExpenseList;
import seedu.address.model.person.Income;
import seedu.address.model.person.IncomeList;
import seedu.address.model.person.UniqueEntryList;
import seedu.address.model.person.Wish;
import seedu.address.model.person.WishList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueEntryList entries;
    private final ExpenseList expenses;
    private final IncomeList incomes;
    private final WishList wishes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        expenses = new ExpenseList();
        entries = new UniqueEntryList();
        incomes = new IncomeList();
        wishes = new WishList();
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
     * Replaces the contents of expenses with {@code expenses}.
     * {@code expenses} must not contain duplicate expenses.
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses.setEntries(expenses);
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes.setEntries(incomes);
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes.setEntries(wishes);
    }



    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setExpenses(newData.getExpenseList());
        setExpenses(newData.getExpenseList());
        setWishes(newData.getWishList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return entries.contains(entry);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    /**
     * Adds a specified Expense to the finance app.
     * @param expense the specified Expense to be added.
     */
    public void addExpense(Expense expense) {
        entries.add(expense);
        expenses.add(expense);
    }

    /**
     * Adds the specified Income to the finance app.
     * @param income the specified Income to be added.
     */
    public void addIncome(Income income) {
        entries.add(income);
        incomes.add(income);
    }

    /**
     * Adds the specified Wish to the finance app.
     * @param wish the specified Wish to be added.
     */
    public void addWish(Wish wish) {
        entries.add(wish);
        wishes.add(wish);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireNonNull(editedEntry);
        entries.setPerson(target, editedEntry);
    }

    /**
     * Replaces the given Expense {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the address book.
     * The expense identity of {@code editedEntry} must not be the same as another existing expense in the address book.
     */
    public void setExpense(Expense target, Expense editedEntry) {
        requireNonNull(editedEntry);
        expenses.setExpense(target, editedEntry);
        entries.setPerson(target, editedEntry);
    }

    /**
     * Replaces the given Income {@code target} in the list with {@code editedIncome}.
     * {@code target} must exist in the address book.
     * The income identity of {@code editedEntry} must not be the same as another existing income in the address book.
     */
    public void setIncome(Income target, Income editedEntry) {
        requireNonNull(editedEntry);
        incomes.setIncome(target, editedEntry);
        entries.setPerson(target, editedEntry);
    }

    /**
     * Replaces the given Income {@code target} in the list with {@code editedIncome}.
     * {@code target} must exist in the address book.
     * The income identity of {@code editedEntry} must not be the same as another existing income in the address book.
     */
    public void setWish(Wish target, Wish editedEntry) {
        requireNonNull(editedEntry);
        wishes.setWish(target, editedEntry);
        entries.setPerson(target, editedEntry);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEntry(Entry key) {
        entries.remove(key);
    }


    /**
     * Removes {@code key} from this {@code expenses}.
     * {@code key} must exist in the address book.
     */
    public void removeExpense(Expense key) {
        expenses.remove(key);
        entries.remove(key);
    }

    /**
     * Removes {@code key} from this {@code incomes}.
     * {@code key} must exist in the address book.
     */
    public void removeIncome(Income key) {
        incomes.remove(key);
        entries.remove(key);
    }

    /**
     * Removes {@code key} from this {@code wishes}.
     * {@code key} must exist in the address book.
     */
    public void removeWish(Wish key) {
        wishes.remove(key);
        entries.remove(key);
    }

    //// util methods

    //// util methods

    //// util methods
    @Override
    public String toString() {
        return entries.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Entry> getEntryList() {
        return entries.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return expenses.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Income> getIncomeList() {
        return incomes.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Wish> getWishList() {
        return wishes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && entries.equals(((AddressBook) other).entries));
    }

    @Override
    public int hashCode() {
        return entries.hashCode();
    }
}
