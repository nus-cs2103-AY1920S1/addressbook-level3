package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;
import seedu.address.model.person.Wish;
import seedu.address.model.person.SortType;
import seedu.address.model.util.EntryComparator;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final SortType sortByDescription = new SortType("description");
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Entry> filteredEntries;
    private final FilteredList<Expense> filteredExpenses;
    private final FilteredList<Income> filteredIncomes;
    private final FilteredList<Wish> filteredWishes;
    private final SortedList<Entry> sortedEntryList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExpenses = new FilteredList<>(this.addressBook.getExpenseList());
        filteredIncomes = new FilteredList<>(this.addressBook.getIncomeList());
        filteredWishes = new FilteredList<>(this.addressBook.getWishList());
        sortedEntryList = new SortedList<>(this.addressBook.getEntryList());
        sortedEntryList.setComparator(new EntryComparator(sortByDescription));
        filteredEntries = new FilteredList<>(sortedEntryList);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return addressBook.hasEntry(entry);
    }

    @Override
    public void deleteEntry(Entry target) {
        addressBook.removeEntry(target);
        if (target instanceof Expense) {
            addressBook.removeExpense((Expense) target);
        } else if (target instanceof Income) {
            addressBook.removeIncome((Income) target);
        } else if (target instanceof Wish) {
            addressBook.removeWish((Wish) target);
        }
    }

    @Override
    public void deleteExpense(Expense target) {
        addressBook.removeEntry(target);
        addressBook.removeExpense(target);
    }

    @Override
    public void deleteIncome(Income target) {
        addressBook.removeEntry(target);
        addressBook.removeIncome(target);
    }

    @Override
    public void deleteWish(Wish target) {
        addressBook.removeEntry(target);
        addressBook.removeWish(target);
    }

    @Override
    public void addEntry(Entry entry) {
        addressBook.addEntry(entry);
        if (entry instanceof Expense) {
            addressBook.addExpense((Expense) entry);
        } else if (entry instanceof Income) {
            addressBook.addIncome((Income) entry);
        } else if (entry instanceof Wish) {
            addressBook.addWish((Wish) entry);
        }
        sortFilteredEntry(sortByDescription);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void addExpense(Expense expense) {
        addressBook.addExpense(expense);
        sortFilteredEntry(sortByDescription);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void addIncome(Income income) {
        addressBook.addIncome(income);
        sortFilteredEntry(sortByDescription);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void addWish(Wish wish) {
        addressBook.addWish(wish);
        sortFilteredEntry(sortByDescription);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);
        addressBook.setEntry(target, editedEntry);
        if (target instanceof Expense) {
            addressBook.setExpense((Expense) target, (Expense) editedEntry);
        } else if (target instanceof Income) {
            addressBook.setIncome((Income) target, (Income) editedEntry);
        } else if (target instanceof Wish) {
            addressBook.setWish((Wish) target, (Wish) editedEntry);
        }
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Entry} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Entry> getFilteredEntryList() {
        return filteredEntries;
    }

    @Override
    public ObservableList<Expense> getFilteredExpenses() {
        return filteredExpenses;
    }

    @Override
    public ObservableList<Income> getFilteredIncomes() {
        return filteredIncomes;
    }

    @Override
    public ObservableList<Wish> getFilteredWishes() {
        return filteredWishes;
    }

    @Override
    public void updateFilteredEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredEntries.setPredicate(predicate);
    }

    @Override
    public void sortFilteredEntry(SortType c) {
        sortedEntryList.setComparator(new EntryComparator(c));
    }

    @Override
    public void updateFilteredExpenses(Predicate<Expense> predicate) {
        requireNonNull(predicate);
        filteredExpenses.setPredicate(predicate);
    }

    @Override
    public void updateFilteredIncomes(Predicate<Income> predicate) {
        requireNonNull(predicate);
        filteredIncomes.setPredicate(predicate);
    }

    @Override
    public void updateFilteredWishes(Predicate<Wish> predicate) {
        requireNonNull(predicate);
        filteredWishes.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredEntries.equals(other.filteredEntries);
    }

}
