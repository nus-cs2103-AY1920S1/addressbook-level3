package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.ExpenseReminder;
import seedu.address.model.person.Income;
import seedu.address.model.person.Wish;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Entry> PREDICATE_SHOW_ALL_ENTRIES = unused -> true;

    Predicate<Expense> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;

    Predicate<Income> PREDICATE_SHOW_ALL_INCOMES = unused -> true;

    Predicate<Wish> PREDICATE_SHOW_ALL_WISHES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasEntry(Entry entry);

    /**
     * Deletes the given entry.
     * The entry must exist in the address book.
     */
    void deleteEntry(Entry target);

    /**
     * Deletes the given expense.
     * The entry must exist in the address book.
     */
    void deleteExpense(Expense target);

    /**
     * Deletes the given income.
     * The income must exist in the address book.
     */
    void deleteIncome(Income target);

    /**
     * Deletes the given wish.
     * The wish must exist in the address book.
     */
    void deleteWish(Wish target);

    void deleteExpenseReminder(ExpenseReminder target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addEntry(Entry entry);

    void addExpense(Expense expense);

    void addIncome(Income income);

    void addWish(Wish wish);

    void addExpenseReminder(ExpenseReminder expenseReminder);
    /**
     * Replaces the given entry {@code target} with {@code editedEntry}.
     * {@code target} must exist in the address book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the address book.
     */
    void setEntry(Entry target, Entry editedEntry);

    void setExpenseReminder(ExpenseReminder target, ExpenseReminder editedEntry);

    /** Returns an unmodifiable view of the filtered entry list */
    ObservableList<Entry> getFilteredEntryList();

    /** Returns an unmodifiable view of the filtered expenditure list */
    ObservableList<Expense> getFilteredExpenses();

    /** Returns an unmodifiable view of the filtered entry list */
    ObservableList<Income> getFilteredIncomes();

    /** Returns an unmodifiable view of the filtered entry list */
    ObservableList<Wish> getFilteredWishes();

    ObservableList<ExpenseReminder> getFilteredReminders();
    /**
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEntryList(Predicate<Entry> predicate);

    void updateFilteredExpenses(Predicate<Expense> predicate);

    void updateFilteredIncomes(Predicate<Income> predicate);

    void updateFilteredWishes(Predicate<Wish> predicate);
}
