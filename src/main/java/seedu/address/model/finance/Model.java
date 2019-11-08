package seedu.address.model.finance;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.finance.budget.Budget;
import seedu.address.model.finance.budget.BudgetData;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<LogEntry> PREDICATE_SHOW_ALL_LOG_ENTRIES = unused -> true;

    Predicate<Budget> PREDICATE_SHOW_ALL_BUDGETS = unused -> true;

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
     * Returns the user prefs' finance log file path.
     */
    Path getFinanceLogFilePath();

    /**
     * Sets the user prefs' finance log file path.
     */
    void setFinanceLogFilePath(Path financeLogFilePath);

    /**
     * Replaces finance log data with the data in {@code Modulo}.
     */
    void setFinanceLog(ReadOnlyFinanceLog financeLog);

    /** Returns the FinanceLog */
    ReadOnlyFinanceLog getFinanceLog();

    /** Set the {@code GraphicsData} object to view. */
    void setGraphicsData(GraphicsData gData);

    /**
     * Returns true if an entry with the same identity as {@code LogEntry} exists in the finance log.
     */
    boolean hasLogEntry(LogEntry logEntry);

    boolean hasBudget(Budget budget);

    /**
     * Deletes the given log entry.
     * The entry must exist in the finance log.
     */
    void deleteLogEntry(LogEntry target);

    void deleteBudget(Budget budget);

    /**
     * Adds the given log entry.
     */
    void addLogEntry(LogEntry logEntry);

    void addBudget(Budget budget);

    /**
     * Replaces the given log entry {@code target} with {@code editedLogEntry}.
     * {@code target} must exist in the finance log.
     */
    void setLogEntry(LogEntry target, LogEntry editedLogEntry);

    /**
     * Marks the given log entry as repaid.
     * {@code LogEntry} must be of type borrow or lend.
     */
    void markLogEntryAsRepaid(LogEntry logEntry);

    /** Returns an unmodifiable view of the filtered list of log entries */
    ObservableList<LogEntry> getFilteredLogEntryList();

    /** Returns an unmodifiable view of the filtered list of budgets */
    ObservableList<Budget> getFilteredBudgetList();

    ObservableList<BudgetData> getFilteredBudgetDataList();

    /** Returns true if any active budget has been exceeded */
    boolean hasAnyActiveBudgetExceeded();

    /** Returns true if any active budget is close to being exceeded */
    boolean hasAnyActiveBudgetCloseToExceed();

    GraphicsData getGraphicsData();

    /**
     * Updates the filter of the filtered list of log entries to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLogEntryList(Predicate<LogEntry> predicate);

    void updateFilteredBudgetList(Predicate<Budget> predicate);

}
