package seedu.guilttrip.model;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.guilttrip.commons.core.GuiSettings;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.core.step.Step;
import seedu.guilttrip.commons.util.AutoExpenseUpdater;
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
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.statistics.CategoryStatistics;
import seedu.guilttrip.model.statistics.DailyStatistics;
import seedu.guilttrip.model.statistics.StatisticsManager;
import seedu.guilttrip.model.util.EntryComparator;

/**
 * Represents the in-memory model of the guiltTrip data.
 */
public class ModelManager implements Model {
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
    private final VersionedGuiltTrip versionedGuiltTrip;

    /**
     * Initializes a ModelManager with the given GuiltTrip and userPrefs.
     */
    public ModelManager(ReadOnlyGuiltTrip guiltTrip, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(guiltTrip, userPrefs);

        logger.fine("Initializing with GuiltTrip: " + guiltTrip + " and user prefs " + userPrefs);

        versionedGuiltTrip = new VersionedGuiltTrip(guiltTrip);
        this.userPrefs = new UserPrefs(userPrefs);
        incomeCategoryList = versionedGuiltTrip.getIncomeCategoryList();
        expenseCategoryList = versionedGuiltTrip.getExpenseCategoryList();
        //ExpenseList
        sortedExpenseList = new SortedList<>(versionedGuiltTrip.getExpenseList());
        sortedExpenseList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredExpenses = new FilteredList<>(sortedExpenseList);
        //IncomeList
        sortedIncomeList = new SortedList<>(versionedGuiltTrip.getIncomeList());
        sortedIncomeList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredIncomes = new FilteredList<>(sortedIncomeList);
        //BudgetList
        sortedBudgetList = new SortedList<>(versionedGuiltTrip.getBudgetList());
        sortedBudgetList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredBudgets = new FilteredList<>(sortedBudgetList);

        sortedWishList = new SortedList<>(versionedGuiltTrip.getWishList());
        sortedWishList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredWishes = new FilteredList<>(sortedWishList);

        //AutoExpense
        sortedAutoExpenseList = new SortedList<>(versionedGuiltTrip.getAutoExpenseList());
        sortedAutoExpenseList.setComparator(new EntryComparator(sortByTime, sortByAsc));
        filteredAutoExpenses = new FilteredList<>(sortedAutoExpenseList);

        filteredReminders = new FilteredList<>(versionedGuiltTrip.getReminderList());
        filteredConditions = new FilteredList<>(versionedGuiltTrip.getConditionList());
        createExpensesFromAutoExpenses();
        this.stats = new StatisticsManager(this.filteredExpenses, this.filteredIncomes,
                versionedGuiltTrip.getCategoryList());
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
    public Path getGuiltTripFilePath() {
        return userPrefs.getGuiltTripFilePath();
    }

    @Override
    public void setGuiltTripFilePath(Path guiltTripFilePath) {
        requireNonNull(guiltTripFilePath);
        userPrefs.setGuiltTripFilePath(guiltTripFilePath);
    }

    // =========== GuiltTrip
    // ================================================================================

    @Override
    public void setGuiltTrip(ReadOnlyGuiltTrip guiltTrip) {
        versionedGuiltTrip.resetData(guiltTrip);
    }

    @Override
    public ReadOnlyGuiltTrip getGuiltTrip() {
        return versionedGuiltTrip;
    }

    @Override
    public boolean hasCategory(Category category) {
        return versionedGuiltTrip.hasCategory(category);
    }

    @Override
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return versionedGuiltTrip.hasBudget(budget);
    }

    @Override
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return versionedGuiltTrip.hasExpense(expense);
    }

    @Override
    public boolean hasIncome(Income income) {
        requireNonNull(income);
        return versionedGuiltTrip.hasIncome(income);
    }

    @Override
    public boolean hasWish(Wish wish) {
        requireNonNull(wish);
        return versionedGuiltTrip.hasWish(wish);
    }

    @Override
    public boolean hasAutoExpense(AutoExpense autoExpense) {
        requireNonNull(autoExpense);
        return versionedGuiltTrip.hasAutoExpense(autoExpense);
    }

    @Override
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return versionedGuiltTrip.hasReminder(reminder);
    }

    @Override
    public boolean hasCondition(Condition condition) {
        requireNonNull(condition);
        return versionedGuiltTrip.hasCondition(condition);
    }

    @Override
    public void deleteCategory(Category target) {
        versionedGuiltTrip.removeCategory(target);
    }

    @Override
    public void deleteExpense(Expense target) {
        versionedGuiltTrip.removeEntry(target);
        versionedGuiltTrip.removeExpense(target);
        versionedGuiltTrip.updateBudgets(filteredExpenses);
        updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);
        sortFilteredExpense(sortByTime, sortByAsc);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteIncome(Income target) {
        versionedGuiltTrip.removeEntry(target);
        versionedGuiltTrip.removeIncome(target);
        updateFilteredIncomes(PREDICATE_SHOW_ALL_INCOMES);
        sortFilteredIncome(sortByTime, sortByAsc);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteWish(Wish target) {
        versionedGuiltTrip.removeWish(target);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
        updateFilteredWishes(PREDICATE_SHOW_ALL_WISHES);
        sortFilteredWishes(sortByTime, sortByAsc);
    }

    @Override
    public void deleteBudget(Budget target) {
        versionedGuiltTrip.removeBudget(target);
        updateFilteredBudgets(PREDICATE_SHOW_ALL_BUDGETS);
        sortFilteredBudget(sortByTime, sortByAsc);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteAutoExpense(AutoExpense target) {
        versionedGuiltTrip.removeEntry(target);
        versionedGuiltTrip.removeAutoExpense(target);
        updateFilteredAutoExpenses(PREDICATE_SHOW_ALL_AUTOEXPENSES);
        sortFilteredAutoExpense(sortByTime, sortByAsc);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteReminder(Reminder target) {
        versionedGuiltTrip.removeReminder(target);
    }

    @Override
    public void deleteCondition(Condition target) {
        versionedGuiltTrip.removeCondition(target);
    }

    @Override
    public void addCategory(Category category) {
        versionedGuiltTrip.addCategory(category);
    }

    @Override
    public void addExpense(Expense expense) {
        versionedGuiltTrip.addExpense(expense);
        versionedGuiltTrip.updateBudgets(filteredExpenses);
        sortFilteredExpense(sortByTime, sortByAsc);
        updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);
    }

    @Override
    public void addIncome(Income income) {
        versionedGuiltTrip.addIncome(income);
        updateFilteredIncomes(PREDICATE_SHOW_ALL_INCOMES);
        sortFilteredIncome(sortByTime, sortByAsc);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void addWish(Wish wish) {
        versionedGuiltTrip.addWish(wish);
        updateFilteredWishes(PREDICATE_SHOW_ALL_WISHES);
        sortFilteredWishes(sortByTime, sortByAsc);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void addAutoExpense(AutoExpense autoExpense) {
        versionedGuiltTrip.addAutoExpense(autoExpense);
        updateFilteredAutoExpenses(PREDICATE_SHOW_ALL_AUTOEXPENSES);
        sortFilteredAutoExpense(sortByTime, sortByAsc);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void addBudget(Budget budget) {
        budget.setSpent(filteredExpenses);
        versionedGuiltTrip.addBudget(budget);
        versionedGuiltTrip.updateBudgets(filteredExpenses);
        updateFilteredBudgets(PREDICATE_SHOW_ALL_BUDGETS);
        sortFilteredBudget(sortByTime, sortByAsc);
        filteredReminders.filtered(PREDICATE_SHOW_ACTIVE_REMINDERS);
        filteredReminders.filtered(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void addReminder(Reminder reminder) {
        versionedGuiltTrip.addReminder(reminder);
    }

    @Override
    public void addCondition(Condition condition) {
        versionedGuiltTrip.addCondition(condition);
    }

    @Override
    public void setCategory(Category target, Category editedCategory) {
        requireAllNonNull(target, editedCategory);
        versionedGuiltTrip.setCategory(target, editedCategory);
    }

    @Override
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireAllNonNull(target, editedReminder);
        versionedGuiltTrip.setReminder(target, editedReminder);
    }

    @Override
    public void setCondition(Condition target, Condition editedCondition) {
        requireAllNonNull(target, editedCondition);
        versionedGuiltTrip.setCondition(target, editedCondition);
    }

    @Override
    public void setExpense(Expense target, Expense editedEntry) {
        requireAllNonNull(target, editedEntry);
        versionedGuiltTrip.setExpense(target, editedEntry);
        versionedGuiltTrip.updateBudgets(filteredExpenses);
        updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);
        sortFilteredExpense(sortByTime, sortByAsc);
    }

    @Override
    public void setAutoExpense(AutoExpense target, AutoExpense editedEntry) {
        requireAllNonNull(target, editedEntry);
        versionedGuiltTrip.setAutoExpense(target, editedEntry);
        updateFilteredAutoExpenses(PREDICATE_SHOW_ALL_AUTOEXPENSES);
        sortFilteredAutoExpense(sortByTime, sortByAsc);
    }

    @Override
    public void setIncome(Income target, Income editedEntry) {
        requireAllNonNull(target, editedEntry);
        versionedGuiltTrip.setIncome(target, editedEntry);
        updateFilteredIncomes(PREDICATE_SHOW_ALL_INCOMES);
        sortFilteredIncome(sortByTime, sortByAsc);
    }

    @Override
    public void setWish(Wish target, Wish editedWish) {
        requireAllNonNull(target, editedWish);
        versionedGuiltTrip.setWish(target, editedWish);
        updateFilteredWishes(PREDICATE_SHOW_ALL_WISHES);
        sortFilteredWishes(sortByTime, sortByAsc);
    }

    @Override
    public void setBudget(Budget target, Budget editedBudget) {
        requireAllNonNull(target, editedBudget);
        versionedGuiltTrip.setBudget(target, editedBudget);
        updateFilteredBudgets(PREDICATE_SHOW_ALL_BUDGETS);
        sortFilteredBudget(sortByTime, sortByAsc);
    }

    @Override
    public boolean categoryHasAnyEntries(Category category) {
        return versionedGuiltTrip.categoryHasAnyEntries(category);
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
        return versionedGuiltTrip.getCategoryList();
    }
    // =========== Filtered Person List Accessors

    /**
     * Returns an unmodifiable view of the list of {@code Entry} backed by the
     * internal list of {@code versionedGuiltTrip}
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

    public ObservableList<Condition> getFilteredConditions() {
        return filteredConditions;
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

    @Override
    public void sortFilteredWishes(SortType c, SortSequence sequence) {
        sortedWishList.setComparator(new EntryComparator(c, sequence));
    }

    @Override
    public void sortFilteredAutoExpense(SortType c, SortSequence sequence) {
        sortedAutoExpenseList.setComparator(new EntryComparator(c, sequence));
    }

    // =================== Filtering =============================================================
    /*Override
    public void updateAllLists(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        updateFilteredAutoExpenses(predicate);
        updateFilteredWishes(predicate);
        updateFilteredExpenses(predicate);
        updateFilteredIncomes(predicate);
        updateFilteredBudgets(predicate);
    }*/

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

    /**
     * return list of reminders matching this condition.
     * @param predicate condition to be matched.
     */
    @Override
    public void updateFilteredAutoExpenses(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredAutoExpenses.setPredicate(predicate);
    }

    /**
     * return list of reminders matching this condition.
     * @param predicate condition to be matched.
     */
    @Override
    public void updateFilteredReminders(Predicate<Reminder> predicate) {
        requireNonNull(predicate);
        filteredReminders.setPredicate(predicate);
    }

    // =========== Undo/Redo =============================================================

    @Override
    public boolean canUndoGuiltTrip(Step step) {
        return versionedGuiltTrip.canUndo(step);
    }

    @Override
    public boolean canRedoGuiltTrip(Step step) {
        return versionedGuiltTrip.canRedo(step);
    }

    @Override
    public void undoGuiltTrip() {
        versionedGuiltTrip.undo();
        versionedGuiltTrip.updateBudgets(filteredExpenses);
    }

    @Override
    public void redoGuiltTrip() {
        versionedGuiltTrip.redo();
        versionedGuiltTrip.updateBudgets(filteredExpenses);
    }

    @Override
    public void commitGuiltTrip() {
        versionedGuiltTrip.commit();
    }

    /**
     * Generates Expenses from AutoExpenses and update the GuiltTrip.
     */
    public void createExpensesFromAutoExpenses() {
        AutoExpenseUpdater autoExpenseUpdater = new AutoExpenseUpdater(this);
        new Thread(autoExpenseUpdater).start();
    }

    // =========== TrackTime =============================================================
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
        return versionedGuiltTrip.equals(other.versionedGuiltTrip) && userPrefs.equals(other.userPrefs)
                && filteredExpenses.equals(other.filteredExpenses) && filteredIncomes.equals(other.filteredIncomes);
    }
}
