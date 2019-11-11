package seedu.guilttrip.logic;

import java.nio.file.Path;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import seedu.guilttrip.commons.core.GuiSettings;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.messages.Notification;
import seedu.guilttrip.model.statistics.CategoryStatistics;
import seedu.guilttrip.model.statistics.DailyStatistics;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the GuiltTrip.
     *
     * @see seedu.guilttrip.model.Model#getGuiltTrip()
     */
    ReadOnlyGuiltTrip getGuiltTrip();

    DoubleProperty getTotalExpenseForPeriod();

    DoubleProperty getTotalIncomeForPeriod();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<DailyStatistics> getListOfStatsForBarChart();

    ObservableList<CategoryStatistics> getListOfStatsForExpense();

    ObservableList<CategoryStatistics> getListOfStatsForIncome();

    ObservableList<Expense> getFilteredExpenseList();

    ObservableList<Income> getFilteredIncomeList();

    ObservableList<AutoExpense> getFilteredAutoExpenseList();

    ObservableList<Wish> getFilteredWishList();

    ObservableList<Reminder> getFilteredReminders();

    Reminder getReminder();

    ObservableList<Notification> getFilteredNotifications();

    ObservableList<Condition> getFilteredConditions();

    ObservableList<Budget> getFilteredBudgetList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' GuiltTrip file path.
     */
    Path getGuiltTripFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

}
