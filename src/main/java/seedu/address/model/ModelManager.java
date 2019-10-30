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
import seedu.address.model.person.Category;
import seedu.address.model.person.CategoryList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;
import seedu.address.model.person.SortSequence;
import seedu.address.model.person.SortType;
import seedu.address.model.person.Wish;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.conditions.Condition;
import seedu.address.model.statistics.StatisticsManager;
import seedu.address.model.util.EntryComparator;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private StatisticsManager stats;
    private final SortType sortByTime = new SortType("time");
    private final SortSequence sortByAsc = new SortSequence("descending");
    private final UserPrefs userPrefs;
    private final ObservableList<Category> incomeCategoryList;
    private final ObservableList<Category> expenseCategoryList;
    private final FilteredList<Entry> filteredEntries;
    private final FilteredList<Expense> filteredExpenses;
    private final FilteredList<Income> filteredIncomes;
    private final FilteredList<Wish> filteredWishes;
    private final FilteredList<Budget> filteredBudgets;
    private final FilteredList<AutoExpense> filteredAutoExpenses;
    private final SortedList<Entry> sortedEntryList;
    private final FilteredList<Reminder> filteredReminders;
    private final FilteredList<Condition> filteredConditions;
    private final VersionedAddressBook versionedAddressBook;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        incomeCategoryList = versionedAddressBook.getIncomeCategoryList();
        expenseCategoryList = versionedAddressBook.getExpenseCategoryList();
        filteredExpenses = new FilteredList<>(versionedAddressBook.getExpenseList());
        filteredIncomes = new FilteredList<>(versionedAddressBook.getIncomeList());
        filteredWishes = new FilteredList<>(versionedAddressBook.getWishList());
        filteredBudgets = new FilteredList<>(versionedAddressBook.getBudgetList());
        filteredAutoExpenses = new FilteredList<>(versionedAddressBook.getAutoExpenseList());
        sortedEntryList = new SortedList<>(versionedAddressBook.getEntryList());
        sortedEntryList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredEntries = new FilteredList<>(sortedEntryList);
        filteredReminders = new FilteredList<>(versionedAddressBook.getReminderList());
        filteredConditions = new FilteredList<>(versionedAddressBook.getConditionList());
        createExpensesfromAutoExpenses();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================
    @Override
    public void setStats(StatisticsManager stats) {
        this.stats = stats;
    }

    @Override
    public StatisticsManager getStats() {
        return stats;
    }

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
    public boolean hasCategory(Category category) {
        return versionedAddressBook.hasCategory(category);
    }

    @Override
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return versionedAddressBook.hasEntry(entry);
    }

    @Override
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return versionedAddressBook.hasBudget(budget);
    }

    @Override
    public boolean hasWish(Wish wish) {
        requireNonNull(wish);
        return versionedAddressBook.hasWish(wish);
    }

    @Override
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return versionedAddressBook.hasReminder(reminder);
    }

    @Override
    public boolean hasCondition(Condition condition) {
        requireNonNull(condition);
        return versionedAddressBook.hasCondition(condition);
    }

    @Override
    public void deleteCategory(Category target) {
        versionedAddressBook.removeCategory(target);
    }

    @Override
    public void deleteEntry(Entry target) {
        versionedAddressBook.removeEntry(target);
        if (target instanceof Expense) {
            versionedAddressBook.removeExpense((Expense) target);
        } else if (target instanceof Income) {
            versionedAddressBook.removeIncome((Income) target);
        } else if (target instanceof Wish) {
            versionedAddressBook.removeWish((Wish) target);
        } else if (target instanceof Budget) {
            versionedAddressBook.removeBudget((Budget) target);
        }
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteExpense(Expense target) {
        versionedAddressBook.removeEntry(target);
        versionedAddressBook.removeExpense(target);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteIncome(Income target) {
        versionedAddressBook.removeEntry(target);
        versionedAddressBook.removeIncome(target);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteWish(Wish target) {
        versionedAddressBook.removeWish(target);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteBudget(Budget target) {
        versionedAddressBook.removeBudget(target);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteAutoExpense(AutoExpense target) {
        versionedAddressBook.removeEntry(target);
        versionedAddressBook.removeAutoExpense(target);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteReminder(Reminder target) {
        versionedAddressBook.removeReminder(target);
    }

    @Override
    public void deleteCondition(Condition target) {
        versionedAddressBook.removeCondition(target);
    }

    @Override
    public void addEntry(Entry entry) {
        versionedAddressBook.addEntry(entry);
        if (entry instanceof Expense) {
            versionedAddressBook.addExpense((Expense) entry);
        } else if (entry instanceof Income) {
            versionedAddressBook.addIncome((Income) entry);
        } else if (entry instanceof Wish) {
            versionedAddressBook.addWish((Wish) entry);
        } else if (entry instanceof Budget) {
            versionedAddressBook.addBudget((Budget) entry);
        } else if (entry instanceof AutoExpense) {
            versionedAddressBook.addAutoExpense((AutoExpense) entry);
        }
        sortFilteredEntry(sortByTime, sortByAsc);
    }

    @Override
    public void addCategory(Category category) {
        versionedAddressBook.addCategory(category);
    }

    @Override
    public void addExpense(Expense expense) {
        versionedAddressBook.addExpense(expense);
        sortFilteredEntry(sortByTime, sortByAsc);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void addIncome(Income income) {
        versionedAddressBook.addIncome(income);
        sortFilteredEntry(sortByTime, sortByAsc);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void addWish(Wish wish) {
        versionedAddressBook.addWish(wish);
        sortFilteredEntry(sortByTime, sortByAsc);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void addAutoExpense(AutoExpense autoExpense) {
        versionedAddressBook.addAutoExpense(autoExpense);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void addBudget(Budget budget) {
        budget.setSpent(filteredExpenses);
        versionedAddressBook.addBudget(budget);
        versionedAddressBook.updateBudgets();
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void addReminder(Reminder reminder) {
        versionedAddressBook.addReminder(reminder);
    }

    @Override
    public void addCondition(Condition condition) {
        versionedAddressBook.addCondition(condition);
    }

    @Override
    public void setCategory(Category target, Category editedCategory) {
        requireAllNonNull(target, editedCategory);
        versionedAddressBook.setCategory(target, editedCategory);
    }

    @Override
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);
        if (target instanceof Expense) {
            //TODO
            Expense toEditEntry = new Expense(editedEntry.getCategory(), editedEntry.getDesc(), editedEntry.getDate(),
                    editedEntry.getAmount(), editedEntry.getTags());
            Expense expenseToEdit = versionedAddressBook.getExpenseList().filtered(t -> t == target).get(0);
            versionedAddressBook.setEntry(expenseToEdit, toEditEntry);
            versionedAddressBook.setExpense(expenseToEdit, toEditEntry);
        } else {
            Income incomeToEdit = versionedAddressBook.getIncomeList().filtered(t -> t == target).get(0);
            Income toEditEntry = new Income(editedEntry.getCategory(), editedEntry.getDesc(), editedEntry.getDate(),
                    editedEntry.getAmount(), editedEntry.getTags());
            versionedAddressBook.setEntry(incomeToEdit, toEditEntry);
            versionedAddressBook.setIncome(incomeToEdit, toEditEntry);
        }
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireAllNonNull(target, editedReminder);
        versionedAddressBook.setReminder(target, editedReminder);
    }

    @Override
    public void setCondition(Condition target, Condition editedCondition) {
        requireAllNonNull(target, editedCondition);
        versionedAddressBook.setCondition(target, editedCondition);
    }

    public void setExpense(Expense target, Expense editedEntry) {
        requireAllNonNull(target, editedEntry);
        versionedAddressBook.setEntry(target, editedEntry);
        versionedAddressBook.setExpense(target, editedEntry);
    }

    @Override
    public void setIncome(Income target, Income editedEntry) {
        requireAllNonNull(target, editedEntry);
        versionedAddressBook.setEntry(target, editedEntry);
        versionedAddressBook.setIncome(target, editedEntry);
    }

    @Override
    public void setWish(Wish target, Wish editedWish) {
        requireAllNonNull(target, editedWish);
        versionedAddressBook.setWish(target, editedWish);
    }

    @Override
    public void setBudget(Budget target, Budget editedBudget) {
        requireAllNonNull(target, editedBudget);
        versionedAddressBook.setBudget(target, editedBudget);
    }

    @Override
    public CategoryList getCategoryList() {
        return versionedAddressBook.getCategoryList();
    }
    // =========== Filtered Person List Accessors

    /**
     * Returns an unmodifiable view of the list of {@code Entry} backed by the
     * internal list of {@code versionedAddressBook}
     */

    @Override
    public ObservableList<Category> getExpenseCategoryList() {
        return expenseCategoryList;
    }

    @Override
    public ObservableList<Category> getIncomeCategoryList() {
        return incomeCategoryList;
    }

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
    public ObservableList<Entry> getFilteredExpensesAndIncomes() {
        return new FilteredList<>(filteredEntries, entry -> entry instanceof Expense || entry instanceof Income);
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

    @Override

    public ObservableList<Reminder> getFilteredReminders() {
        return filteredReminders;
    }

    public ObservableList<Condition> getFilteredConditions() {
        return filteredConditions;
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
        for (Budget budget : filteredBudgets) {
            budget.updateSpent();
        }
    }

    @Override
    /**
     * return list of reminders matching this condition.
     * @param predicate condition to be matched.
     */
    public void updateFilteredAutoExpenses(Predicate<AutoExpense> predicate) {
        requireNonNull(predicate);
        filteredAutoExpenses.setPredicate(predicate);
    }

    @Override
    /**
     * return list of reminders matching this condition.
     * @param predicate condition to be matched.
     */
    public void updateFilteredReminders(Predicate<Reminder> predicate) {
        requireNonNull(predicate);
        filteredReminders.setPredicate(predicate);
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

    private void createExpensesfromAutoExpenses() {
        for (AutoExpense autoExpense : filteredAutoExpenses) {
            filteredExpenses.addAll(autoExpense.generateNewExpenses());
        }
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
