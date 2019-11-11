package seedu.guilttrip.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import seedu.guilttrip.commons.core.GuiSettings;
import seedu.guilttrip.commons.core.step.Step;
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

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Entry> PREDICATE_SHOW_ALL_ENTRIES = unused -> true;

    Predicate<Entry> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;
    Predicate<Entry> PREDICATE_SHOW_ALL_INCOMES = unused -> true;
    Predicate<Entry> PREDICATE_SHOW_ALL_WISHES = unused -> true;
    Predicate<Entry> PREDICATE_SHOW_ALL_BUDGETS = unused -> true;
    Predicate<Entry> PREDICATE_SHOW_ALL_AUTOEXPENSES = unused -> true;
    Predicate<Entry> PREDICATE_SHOW_ALL_CONDITIONS = unused -> true;
    Predicate<Reminder> PREDICATE_SHOW_ALL_REMINDERS = unused -> true;
    Predicate<Reminder> PREDICATE_SHOW_GENERAL_REMINDERS = reminder -> reminder instanceof GeneralReminder;

    DoubleProperty getTotalExpenseForPeriod();

    DoubleProperty getTotalIncomeForPeriod();

    ObservableList<DailyStatistics> getListOfStatsForBarChart();

    ObservableList<CategoryStatistics> getListOfStatsForExpense();

    ObservableList<CategoryStatistics> getListOfStatsForIncome();

    void updateListOfStats();

    void updateListOfStats(List<Date> period);

    void updateBarCharts();

    void updateBarCharts(Date month);

    void createExpensesFromAutoExpenses();

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
     * Returns the user prefs' guiltTrip book file path.
     */
    Path getGuiltTripFilePath();

    /**
     * Sets the user prefs' guiltTrip file path.
     */
    void setGuiltTripFilePath(Path guiltTripFilePath);

    /**
     * Replaces guilttrip book data with the data in {@codh guiltTrip}.
     */
    void setGuiltTrip(ReadOnlyGuiltTrip guiltTrip);

    /** Returns the GuiltTrip */
    ReadOnlyGuiltTrip getGuiltTrip();

    /**
     * Returns true if a entry with the same identity as {@code entry} exists in
     * the guilttrip book.
     */
    boolean hasCategory(Category category);

    boolean categoryHasAnyEntries(Category category);
    /**
     * Returns true if a entry with the same identity as {@code entry} exists in
     * the guilttrip book.
     */
    boolean hasReminder(Reminder generalReminder);

    boolean hasCondition(Condition condition);

    boolean hasBudget(Budget budget);

    boolean hasWish(Wish wish);

    boolean hasExpense(Expense expense);

    boolean hasIncome(Income income);

    boolean hasAutoExpense(AutoExpense expense);


    /**
     * Deletes the given category. The category must exist in the guilttrip book.
     */
    void deleteCategory(Category target);

    /**
     * Deletes the given expense. The entry must exist in the guilttrip book.
     */
    void deleteExpense(Expense target);

    /**
     * Deletes the given income. The income must exist in the guilttrip book.
     */
    void deleteIncome(Income target);

    /**
     * Deletes the given wish. The wish must exist in the guilttrip book.
     */
    void deleteWish(Wish target);

    void deleteReminder(Reminder target);

    void deleteCondition(Condition target);

    /**
     * Deletes the given budget.
     * The budget must exist in the guilttrip book.
     */
    void deleteBudget(Budget target);

    /**
     * Deletes the given AutoExpense. The entry must exist in the guilttrip book.
     */
    void deleteAutoExpense(AutoExpense target);

    /**
     * Adds the given entry. {@code entry} must not already exist in the guilttrip
     * book.
     */
    void addCategory(Category category);

    void addExpense(Expense expense);

    void addIncome(Income income);

    void addWish(Wish wish);

    void addBudget(Budget budget);

    void addAutoExpense(AutoExpense autoExpense);

    void addReminder(Reminder generalReminder);

    void addCondition(Condition condition);

    void setCategory(Category target, Category editedCategory);

    /**
     * Replaces the given entry {@code target} with {@code editedEntry}.
     * {@code target} must exist in the guilttrip book. The entry identity of
     * {@code editedEntry} must not be the same as another existing entry in the
     * guilttrip book.
     */

    void setReminder(Reminder target, Reminder editedEntry);

    void setCondition(Condition target, Condition editedEntry);

    void setIncome(Income target, Income editedEntry);

    void setExpense(Expense target, Expense editedEntry);

    void setWish(Wish target, Wish editedWish);

    void setBudget(Budget target, Budget editedbudget);

    void setAutoExpense(AutoExpense target, AutoExpense editedbudget);

    CategoryList getCategoryList();

    /** Returns an unmodifiable view of the income category list */
    ObservableList<Category> getIncomeCategoryList();

    /** Returns an unmodifiable view of the expense category list */
    ObservableList<Category> getExpenseCategoryList();

    /** Returns an unmodifiable view of the filtered expenditure list */
    ObservableList<Expense> getFilteredExpenses();

    /** Returns an unmodifiable view of the filtered entry list */
    ObservableList<Income> getFilteredIncomes();

    /** Returns an unmodifiable view of the filtered entry list */
    ObservableList<Wish> getFilteredWishes();

    /** Returns an unmodifiable view of the filtered budget list */
    ObservableList<Budget> getFilteredBudgets();

    /** Returns an unmodifiable view of the filtered expenditure list */
    ObservableList<AutoExpense> getFilteredAutoExpenses();

    /** Returns an unmodifiable view of the filtered generalReminder list */
    ObservableList<Reminder> getFilteredReminders();

    /**Gets notifications of reminders*/
    ObservableList<Notification> getFilteredNotifications();

    /**Gets conditions of selected reminders*/
    ObservableList<Condition> getFilteredConditions();

    /**Return selected generalReminder for modification */
    Reminder getReminderSelected();

    /**Select a generalReminder for modification */
    void selectReminder(Reminder generalReminder);

    /**
     * Updates the filter of the filtered entry list to filter by the given
     * {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    //void updateAllLists(Predicate<Entry> predicate);

    void updateFilteredExpenses(Predicate<Entry> predicate);

    void updateFilteredIncomes(Predicate<Entry> predicate);

    void updateFilteredWishes(Predicate<Entry> predicate);

    void updateFilteredBudgets(Predicate<Entry> predicate);

    void updateFilteredAutoExpenses(Predicate<Entry> predicate);

    void updateFilteredReminders(Predicate<Reminder> predicate);

    void sortFilteredExpense(SortType comparator, SortSequence sequence);

    void sortFilteredIncome(SortType comparator, SortSequence sequence);

    void sortFilteredBudget(SortType comparator, SortSequence sequence);

    void sortFilteredAutoExpense(SortType comparator, SortSequence sequence);

    void sortFilteredWishes(SortType comparator, SortSequence sequence);

    /**
     * Returns true if the model has previous finance tracker states to restore.
     */
    boolean canUndoGuiltTrip(Step step);

    /**
     * Returns true if the model has undone finance tracker states to restore.
     */
    boolean canRedoGuiltTrip(Step step);

    /**
     * Restores the model's finance tracker to its previous state.
     */
    void undoGuiltTrip();

    /**
     * Restores the model's finance tracker to its previously undone state.
     */
    void redoGuiltTrip();

    /**
     * Saves the current finance tracker state for undo/redo
     */
    void commitGuiltTrip();

}
