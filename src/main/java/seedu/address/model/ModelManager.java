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
import seedu.address.model.person.AutoExpense;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.ExpenseReminder;
import seedu.address.model.person.ExpenseTrackerManager;
import seedu.address.model.person.Income;
import seedu.address.model.person.SortSequence;
import seedu.address.model.person.SortType;
import seedu.address.model.person.Wish;
import seedu.address.model.person.WishReminder;
import seedu.address.model.util.EntryComparator;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final SortType sortByTime = new SortType("time");
    private final SortSequence sortByAsc = new SortSequence("descending");
    private final UserPrefs userPrefs;
    private final FilteredList<Entry> filteredEntries;
    private final FilteredList<Expense> filteredExpenses;
    private final FilteredList<Income> filteredIncomes;
    private final FilteredList<Wish> filteredWishes;
    private final FilteredList<Budget> filteredBudgets;
    private final FilteredList<AutoExpense> filteredAutoExpenses;
    private final SortedList<Entry> sortedEntryList;
    private final FilteredList<ExpenseReminder> filteredExpenseReminders;
    private final ExpenseTrackerManager expenseTrackers;
    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<WishReminder> filteredWishReminders;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExpenses = new FilteredList<>(versionedAddressBook.getExpenseList());
        filteredIncomes = new FilteredList<>(versionedAddressBook.getIncomeList());
        filteredWishes = new FilteredList<>(versionedAddressBook.getWishList());
        filteredBudgets = new FilteredList<>(versionedAddressBook.getBudgetList());
        filteredAutoExpenses = new FilteredList<>(versionedAddressBook.getAutoExpenseList());
        sortedEntryList = new SortedList<>(versionedAddressBook.getEntryList());
        sortedEntryList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredEntries = new FilteredList<>(sortedEntryList);
        filteredExpenseReminders = new FilteredList<>(versionedAddressBook.getExpenseReminderList());
        filteredWishReminders = new FilteredList<>(versionedAddressBook.getWishReminderList());
        expenseTrackers = new ExpenseTrackerManager(versionedAddressBook.getExpenseTrackerList());
        expenseTrackers.track(filteredExpenses);
        versionedAddressBook.updateExpenseReminders();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================

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

    // =========== AddressBook
    // ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return versionedAddressBook.hasEntry(entry);
    }

    @Override
    public boolean hasExpenseReminder(ExpenseReminder reminder) {
        requireNonNull(reminder);
        return versionedAddressBook.hasExpenseReminder(reminder);
    }

    @Override
    public void deleteEntry(Entry target) {
        versionedAddressBook.removeEntry(target);
        if (target instanceof Expense) {
            versionedAddressBook.removeExpense((Expense) target);
            expenseTrackers.track(filteredExpenses);
            versionedAddressBook.updateExpenseReminders();
        } else if (target instanceof Income) {
            versionedAddressBook.removeIncome((Income) target);
        } else if (target instanceof Wish) {
            versionedAddressBook.removeWish((Wish) target);
        } else if (target instanceof Budget) {
            versionedAddressBook.removeBudget((Budget) target);
        }
    }

    @Override
    public void deleteExpense(Expense target) {
        versionedAddressBook.removeEntry(target);
        versionedAddressBook.removeExpense(target);
        expenseTrackers.track(filteredExpenses);
        versionedAddressBook.updateExpenseReminders();
    }

    @Override
    public void deleteIncome(Income target) {
        versionedAddressBook.removeEntry(target);
        versionedAddressBook.removeIncome(target);
    }

    @Override
    public void deleteWish(Wish target) {
        versionedAddressBook.removeEntry(target);
        versionedAddressBook.removeWish(target);
    }

    @Override
    public void deleteBudget(Budget target) {
        versionedAddressBook.removeEntry(target);
        versionedAddressBook.removeBudget(target);
    }

    @Override
    public void deleteAutoExpense(AutoExpense target) {
        versionedAddressBook.removeEntry(target);
        versionedAddressBook.removeAutoExpense(target);
    }

    public void deleteExpenseReminder(ExpenseReminder target) {
        versionedAddressBook.removeExpenseReminder(target);
    }

    @Override
    public void addEntry(Entry entry) {
        versionedAddressBook.addEntry(entry);
        if (entry instanceof Expense) {
            versionedAddressBook.addExpense((Expense) entry);
            expenseTrackers.track(filteredExpenses);
            versionedAddressBook.updateExpenseReminders();
        } else if (entry instanceof Income) {
            versionedAddressBook.addIncome((Income) entry);
        } else if (entry instanceof Wish) {
            versionedAddressBook.addWish((Wish) entry);
        } else if (entry instanceof Budget) {
            versionedAddressBook.addBudget((Budget) entry);
        }
        sortFilteredEntry(sortByTime, sortByAsc);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void addExpense(Expense expense) {
        versionedAddressBook.addExpense(expense);
        sortFilteredEntry(sortByTime, sortByAsc);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        expenseTrackers.track(filteredExpenses);
        versionedAddressBook.updateExpenseReminders();
    }

    @Override
    public void addIncome(Income income) {
        versionedAddressBook.addIncome(income);
        sortFilteredEntry(sortByTime, sortByAsc);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void addWish(Wish wish) {
        versionedAddressBook.addWish(wish);
        sortFilteredEntry(sortByTime, sortByAsc);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void addAutoExpense(AutoExpense autoExpense) {
        versionedAddressBook.addAutoExpense(autoExpense);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void addBudget(Budget budget) {
        versionedAddressBook.addBudget(budget);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void addExpenseReminder(ExpenseReminder expenseReminder) {
        versionedAddressBook.addExpenseReminder(expenseReminder);
        expenseTrackers.track(filteredExpenses);
        versionedAddressBook.updateExpenseReminders();
    }

    @Override
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);
        versionedAddressBook.setEntry(target, editedEntry);
        if (target instanceof Expense) {
            versionedAddressBook.setExpense((Expense) target, (Expense) editedEntry);
            expenseTrackers.track(filteredExpenses);
            versionedAddressBook.updateExpenseReminders();
        } else if (target instanceof Income) {
            versionedAddressBook.setIncome((Income) target, (Income) editedEntry);
        } else if (target instanceof Wish) {
            versionedAddressBook.setWish((Wish) target, (Wish) editedEntry);
        } else if (target instanceof Budget) {
            versionedAddressBook.setBudget((Budget) target, (Budget) editedEntry);
        }
    }

    @Override
    public void setExpenseReminder(ExpenseReminder target, ExpenseReminder editedEntry) {
        requireAllNonNull(target, editedEntry);
        versionedAddressBook.setExpenseReminder(target, editedEntry);
        expenseTrackers.track(filteredExpenses);
        versionedAddressBook.updateExpenseReminders();
    }


    // =========== Filtered Person List Accessors

    /**
     * Returns an unmodifiable view of the list of {@code Entry} backed by the
     * internal list of {@code versionedAddressBook}
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
    public ObservableList<Budget> getFilteredBudgets() {
        return filteredBudgets;
    }
    @Override
    public ObservableList<AutoExpense> getFilteredAutoExpenses() {
        return filteredAutoExpenses;
    }

    public ObservableList<ExpenseReminder> getFilteredExpenseReminders() {
        return filteredExpenseReminders;
    }


    public ObservableList<WishReminder> getFiltereWishReminders() {
        return filteredWishReminders;
    }

    /**
     * return List of Entries matching condition.
     * @param predicate predicate to filter
     */
    public void updateFilteredEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredEntries.setPredicate(predicate);
    }

    @Override
    public void sortFilteredEntry(SortType c, SortSequence sequence) {
        sortedEntryList.setComparator(new EntryComparator(c, sequence));
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
    public void updateFilteredBudgets(Predicate<Budget> predicate) {
        requireNonNull(predicate);
        filteredBudgets.setPredicate(predicate);
    }

    /**
     * return list of reminders matching this condition.
     * @param predicate condition to be matched.
     */
    public void updateFilteredAutoExpenses(Predicate<AutoExpense> predicate) {
        requireNonNull(predicate);
        filteredAutoExpenses.setPredicate(predicate);
    }

    /**
     * return list of reminders matching this condition.
     * @param predicate condition to be matched.
     */
    public void updateFilteredExpenseReminders(Predicate<ExpenseReminder> predicate) {
        requireNonNull(predicate);
        filteredExpenseReminders.setPredicate(predicate);
    }

    // =========== Undo/Redo =============================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public void updateFilteredWishReminders(Predicate<WishReminder> predicate) {
        requireNonNull(predicate);
        filteredWishReminders.setPredicate(predicate);
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
        return versionedAddressBook.equals(other.versionedAddressBook) && userPrefs.equals(other.userPrefs)
                && filteredEntries.equals(other.filteredEntries);
    }

}
