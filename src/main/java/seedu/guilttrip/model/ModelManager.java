package seedu.guilttrip.model;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.CollectionUtil.requireAllNonNull;

import java.beans.PropertyChangeEvent;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.guilttrip.commons.core.GuiSettings;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.core.step.Step;
import seedu.guilttrip.commons.util.ListenerSupport;
import seedu.guilttrip.commons.util.ObservableSupport;
import seedu.guilttrip.commons.util.TimeUtil;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.CategoryList;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.SortSequence;
import seedu.guilttrip.model.entry.SortType;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.messages.Notification;
import seedu.guilttrip.model.statistics.CategoryStatistics;
import seedu.guilttrip.model.statistics.DailyStatistics;
import seedu.guilttrip.model.statistics.StatisticsManager;
import seedu.guilttrip.model.util.EntryComparator;

/**
 * Represents the in-memory model of the guilttrip book data.
 */
public class ModelManager implements Model, ListenerSupport {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private StatisticsManager stats;
    private final SortType sortByTime = new SortType("time");
    private final SortSequence sortByAsc = new SortSequence("descending");
    private final UserPrefs userPrefs;
    private final ObservableList<Category> incomeCategoryList;
    private final ObservableList<Category> expenseCategoryList;
    private final FilteredList<Expense> filteredExpenses;
    private final FilteredList<Income> filteredIncomes;
    private final FilteredList<Wish> filteredWishes;
    private final FilteredList<Budget> filteredBudgets;
    private final FilteredList<AutoExpense> filteredAutoExpenses;
    private final SortedList<Expense> sortedExpenseList;
    private final SortedList<Income> sortedIncomeList;
    private final SortedList<Budget> sortedBudgetList;
    private final SortedList<AutoExpense> sortedAutoExpenseList;
    private final SortedList<Wish> sortedWishList;
    private final FilteredList<Reminder> filteredReminders;
    private final FilteredList<Condition> filteredConditions;
    private final FilteredList<Notification> filteredNotifications;
    private final VersionedGuiltTrip versionedAddressBook;
    private LocalDate currDate;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyGuiltTrip addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with guilttrip book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedGuiltTrip(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        incomeCategoryList = versionedAddressBook.getIncomeCategoryList();
        expenseCategoryList = versionedAddressBook.getExpenseCategoryList();
        //ExpenseList
        sortedExpenseList = new SortedList<>(versionedAddressBook.getExpenseList());
        sortedExpenseList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredExpenses = new FilteredList<>(sortedExpenseList);
        //IncomeList
        sortedIncomeList = new SortedList<>(versionedAddressBook.getIncomeList());
        sortedIncomeList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredIncomes = new FilteredList<>(sortedIncomeList);
        //BudgetList
        sortedBudgetList = new SortedList<>(versionedAddressBook.getBudgetList());
        sortedBudgetList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredBudgets = new FilteredList<>(sortedBudgetList);

        sortedWishList = new SortedList<>(versionedAddressBook.getWishList());
        sortedWishList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredWishes = new FilteredList<>(sortedWishList);

        //AutoExpense
        sortedAutoExpenseList = new SortedList<>(versionedAddressBook.getAutoExpenseList());
        sortedAutoExpenseList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredAutoExpenses = new FilteredList<>(sortedAutoExpenseList);
        //Reminders
        filteredReminders = new FilteredList<>(versionedAddressBook.getReminderList());
        filteredConditions = new FilteredList<>(versionedAddressBook.getConditionList());
        filteredNotifications = new FilteredList<>(versionedAddressBook.getNotificationList());
        createExpensesfromAutoExpenses();
        this.stats = new StatisticsManager(this.filteredExpenses, this.filteredIncomes,
                versionedAddressBook.getCategoryList());
        TimeUtil.addPropertyChangeListener(this);
        TimeUtil.manualUpdate();
    }

    public ModelManager() {
        this(new GuiltTrip(false), new UserPrefs());
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

    // =========== GuiltTrip
    // ================================================================================

    @Override
    public void setAddressBook(ReadOnlyGuiltTrip addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyGuiltTrip getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasCategory(Category category) {
        return versionedAddressBook.hasCategory(category);
    }

    @Override
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return versionedAddressBook.hasBudget(budget);
    }

    @Override
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return versionedAddressBook.hasExpense(expense);
    }

    @Override
    public boolean hasIncome(Income income) {
        requireNonNull(income);
        return versionedAddressBook.hasIncome(income);
    }

    @Override
    public boolean hasWish(Wish wish) {
        requireNonNull(wish);
        return versionedAddressBook.hasWish(wish);
    }

    @Override
    public boolean hasAutoExpense(AutoExpense autoExpense) {
        requireNonNull(autoExpense);
        return versionedAddressBook.hasAutoExpense(autoExpense);
    }

    @Override
    public boolean hasReminder(Reminder Reminder) {
        requireNonNull(Reminder);
        return versionedAddressBook.hasReminder(Reminder);
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
    public void deleteExpense(Expense target) {
        versionedAddressBook.removeEntry(target);
        versionedAddressBook.removeExpense(target);
        versionedAddressBook.updateBudgets(filteredExpenses);
        updateFilteredExpenses(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredExpense(sortByTime, sortByAsc);
    }

    @Override
    public void deleteIncome(Income target) {
        versionedAddressBook.removeEntry(target);
        versionedAddressBook.removeIncome(target);
        updateFilteredIncomes(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredIncome(sortByTime, sortByAsc);
    }

    @Override
    public void deleteWish(Wish target) {
        versionedAddressBook.removeWish(target);
        updateFilteredWishes(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredWishes(sortByTime, sortByAsc);
    }

    @Override
    public void deleteBudget(Budget target) {
        versionedAddressBook.removeBudget(target);
        updateFilteredBudgets(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredBudget(sortByTime, sortByAsc);
    }

    @Override
    public void deleteAutoExpense(AutoExpense target) {
        versionedAddressBook.removeEntry(target);
        versionedAddressBook.removeAutoExpense(target);
        updateFilteredAutoExpenses(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredAutoExpense(sortByTime, sortByAsc);
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
    public void addCategory(Category category) {
        versionedAddressBook.addCategory(category);
    }

    @Override
    public void addExpense(Expense expense) {
        versionedAddressBook.addExpense(expense);
        versionedAddressBook.updateBudgets(filteredExpenses);
        sortFilteredExpense(sortByTime, sortByAsc);
        updateFilteredExpenses(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void addIncome(Income income) {
        versionedAddressBook.addIncome(income);
        updateFilteredIncomes(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredIncome(sortByTime, sortByAsc);
    }

    @Override
    public void addWish(Wish wish) {
        versionedAddressBook.addWish(wish);
        updateFilteredWishes(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredWishes(sortByTime, sortByAsc);
    }

    @Override
    public void addAutoExpense(AutoExpense autoExpense) {
        versionedAddressBook.addAutoExpense(autoExpense);
        updateFilteredAutoExpenses(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredAutoExpense(sortByTime, sortByAsc);
    }

    @Override
    public void addBudget(Budget budget) {
        budget.setSpent(filteredExpenses);
        versionedAddressBook.addBudget(budget);
        versionedAddressBook.updateBudgets(filteredExpenses);
        updateFilteredBudgets(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredBudget(sortByTime, sortByAsc);
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
    public void setReminder(Reminder target, Reminder editedGeneralReminder) {
        requireAllNonNull(target, editedGeneralReminder);
        versionedAddressBook.setReminder(target, editedGeneralReminder);
    }

    @Override
    public void setCondition(Condition target, Condition editedCondition) {
        requireAllNonNull(target, editedCondition);
        versionedAddressBook.setCondition(target, editedCondition);
    }

    @Override
    public void setExpense(Expense target, Expense editedEntry) {
        requireAllNonNull(target, editedEntry);
        versionedAddressBook.setExpense(target, editedEntry);
        versionedAddressBook.updateBudgets(filteredExpenses);
        updateFilteredExpenses(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredExpense(sortByTime, sortByAsc);
    }

    @Override
    public void setAutoExpense(AutoExpense target, AutoExpense editedEntry) {
        requireAllNonNull(target, editedEntry);
        versionedAddressBook.setAutoExpense(target, editedEntry);
        updateFilteredAutoExpenses(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredAutoExpense(sortByTime, sortByAsc);
    }

    @Override
    public void setIncome(Income target, Income editedEntry) {
        requireAllNonNull(target, editedEntry);
        versionedAddressBook.setIncome(target, editedEntry);
        updateFilteredIncomes(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredIncome(sortByTime, sortByAsc);
    }

    @Override
    public void setWish(Wish target, Wish editedWish) {
        requireAllNonNull(target, editedWish);
        versionedAddressBook.setWish(target, editedWish);
        updateFilteredWishes(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredWishes(sortByTime, sortByAsc);
    }

    @Override
    public void setBudget(Budget target, Budget editedBudget) {
        requireAllNonNull(target, editedBudget);
        versionedAddressBook.setBudget(target, editedBudget);
        updateFilteredBudgets(PREDICATE_SHOW_ALL_ENTRIES);
        sortFilteredBudget(sortByTime, sortByAsc);
    }

    @Override
    public void updateListOfStats() {
        this.stats.updateListOfStats();
    }

    @Override
    public void updateListOfStats(List<Date> period) {
        this.stats.updateListOfStats(period);
    }

    @Override
    public void updateBarCharts() {
        this.stats.updateBarCharts();
    }

    @Override
    public void updateBarCharts(Date month) {
        this.stats.updateBarCharts(month);
    }

    @Override
    public DoubleProperty getTotalExpenseForPeriod() {
        return this.stats.getTotalExpenseForPeriod();
    }

    @Override
    public DoubleProperty getTotalIncomeForPeriod() {
        return this.stats.getTotalIncomeForPeriod();
    }

    @Override
    public ObservableList<DailyStatistics> getListOfStatsForBarChart() {
        return this.stats.getListOfStatsForBarChart();
    }

    @Override
    public ObservableList<CategoryStatistics> getListOfStatsForExpense() {
        return this.stats.getListOfStatsForExpense();
    }

    @Override
    public ObservableList<CategoryStatistics> getListOfStatsForIncome() {
        return this.stats.getListOfStatsForIncome();
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

    @Override

    public ObservableList<Reminder> getFilteredReminders() {
        return filteredReminders;
    }

    @Override

    public ObservableList<Notification> getFilteredNotifications() {
        return filteredNotifications;
    }

    @Override
    public ObservableList<Condition> getFilteredConditions() {
        Reminder reminder = getReminderSelected();
        if (reminder instanceof GeneralReminder) {
            ObservableList<Condition> conditions = FXCollections.
                    observableArrayList(((GeneralReminder) reminder).getConditions());
            return conditions;
        } else {
            return FXCollections.
                    observableArrayList(new ArrayList<>());
        }
    }

    //===== GeneralReminder Handler =====//
    @Override
    public Reminder getReminderSelected() {
        return versionedAddressBook.getReminderSelected();
    }

    @Override
    public void selectReminder(Reminder reminder) {
        versionedAddressBook.selectReminder(reminder);
    }
    // =================== Sorting =============================================================

    @Override
    public void sortFilteredExpense(SortType c, SortSequence sequence) {
        sortedExpenseList.setComparator(new EntryComparator(c, sequence));
    }

    @Override
    public void sortFilteredIncome(SortType c, SortSequence sequence) {
        sortedIncomeList.setComparator(new EntryComparator(c, sequence));
    }

    @Override
    public void sortFilteredBudget(SortType c, SortSequence sequence) {
        sortedBudgetList.setComparator(new EntryComparator(c, sequence));
    }

    public void sortFilteredWishes(SortType c, SortSequence sequence) {
        sortedWishList.setComparator(new EntryComparator(c, sequence));
    }

    @Override
    public void sortFilteredAutoExpense(SortType c, SortSequence sequence) {
        sortedAutoExpenseList.setComparator(new EntryComparator(c, sequence));
    }

    // =================== Filtering =============================================================
    @Override
    public void updateAllLists(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        updateFilteredAutoExpenses(predicate);
        updateFilteredWishes(predicate);
        updateFilteredExpenses(predicate);
        updateFilteredIncomes(predicate);
        updateFilteredBudgets(predicate);
    }

    @Override
    public void updateFilteredExpenses(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredExpenses.setPredicate(predicate);
    }

    @Override
    public void updateFilteredIncomes(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredIncomes.setPredicate(predicate);
    }

    @Override
    public void updateFilteredWishes(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredWishes.setPredicate(predicate);
    }

    @Override
    public void updateFilteredBudgets(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredBudgets.setPredicate(predicate);
        for (Budget budget : filteredBudgets) {
            budget.setSpent(filteredExpenses);
            budget.updateSpent();
        }
    }

    @Override
    /**
     * return list of reminders matching this condition.
     * @param predicate condition to be matched.
     */
    public void updateFilteredAutoExpenses(Predicate<Entry> predicate) {
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
    public boolean canUndoAddressBook(Step step) {
        return versionedAddressBook.canUndo(step);
    }

    @Override
    public boolean canRedoAddressBook(Step step) {
        return versionedAddressBook.canRedo(step);
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        versionedAddressBook.updateBudgets(filteredExpenses);
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        versionedAddressBook.updateBudgets(filteredExpenses);
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    private void createExpensesfromAutoExpenses() {
        for (AutoExpense autoExpense : filteredAutoExpenses) {
            autoExpense.generateNewExpenses().stream().forEach(this::addExpense);
        }
    }

    // =========== TrackTime =============================================================

    @Override
    public void propertyChange(ObservableSupport.Evt evt) {
        if (evt.getPropertyName().equalsIgnoreCase("currDate")) {
            if ((currDate == null) || !currDate.equals(evt.getNewValue())) {
                createExpensesfromAutoExpenses();
            }
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
                && filteredExpenses.equals(other.filteredExpenses) && filteredIncomes.equals(other.filteredIncomes);
    }
}
