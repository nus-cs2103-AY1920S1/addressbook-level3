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
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.messages.Notification;
import seedu.guilttrip.model.statistics.CategoryStatistics;
import seedu.guilttrip.model.statistics.DailyStatistics;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public boolean categoryHasAnyEntries(Category cat) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public DoubleProperty getTotalExpenseForPeriod() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public DoubleProperty getTotalIncomeForPeriod() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<DailyStatistics> getListOfStatsForBarChart() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<CategoryStatistics> getListOfStatsForExpense() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<CategoryStatistics> getListOfStatsForIncome() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateListOfStats() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateListOfStats(List<Date> period) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateBarCharts() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateBarCharts(Date month) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void createExpensesFromAutoExpenses() {
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getGuiltTripFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiltTripFilePath(Path guiltTripFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiltTrip(ReadOnlyGuiltTrip guiltTrip) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyGuiltTrip getGuiltTrip() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasCategory(Category category) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasReminder(Reminder reminder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasCondition(Condition condition) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasBudget(Budget budget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasWish(Wish wish) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasExpense(Expense expense) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasIncome(Income income) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAutoExpense(AutoExpense expense) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteCategory(Category target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteExpense(Expense target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteIncome(Income target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteWish(Wish target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteReminder(Reminder target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteCondition(Condition target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteBudget(Budget target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteAutoExpense(AutoExpense target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addCategory(Category category) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addExpense(Expense expense) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addIncome(Income income) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addWish(Wish wish) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addBudget(Budget budget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addAutoExpense(AutoExpense autoExpense) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addReminder(Reminder reminder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addCondition(Condition condition) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCategory(Category target, Category editedCategory) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setReminder(Reminder target, Reminder editedEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCondition(Condition target, Condition editedEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setIncome(Income target, Income editedEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setExpense(Expense target, Expense editedEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setWish(Wish target, Wish editedWish) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setBudget(Budget target, Budget editedbudget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAutoExpense(AutoExpense target, AutoExpense editedbudget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CategoryList getCategoryList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Category> getIncomeCategoryList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Category> getExpenseCategoryList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Expense> getFilteredExpenses() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Income> getFilteredIncomes() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Wish> getFilteredWishes() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Budget> getFilteredBudgets() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<AutoExpense> getFilteredAutoExpenses() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Reminder> getFilteredReminders() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Condition> getFilteredConditions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Notification> getFilteredNotifications() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Reminder getReminderSelected() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void selectReminder(Reminder generalReminder) {

    }

    /*@Override
    public void updateAllLists(Predicate<Entry> predicate) {
        throw new AssertionError("This method should not be called.");
    }*/

    @Override
    public void updateFilteredExpenses(Predicate<Entry> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredIncomes(Predicate<Entry> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredWishes(Predicate<Entry> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredBudgets(Predicate<Entry> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredAutoExpenses(Predicate<Entry> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredReminders(Predicate<Reminder> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFilteredExpense(SortType comparator, SortSequence sequence) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFilteredIncome(SortType comparator, SortSequence sequence) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFilteredBudget(SortType comparator, SortSequence sequence) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFilteredAutoExpense(SortType comparator, SortSequence sequence) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFilteredWishes(SortType comparator, SortSequence sequence) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoGuiltTrip(Step step) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoGuiltTrip(Step step) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoGuiltTrip() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoGuiltTrip() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitGuiltTrip() {
        throw new AssertionError("This method should not be called.");
    }


}
