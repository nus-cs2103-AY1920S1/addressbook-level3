package seedu.address.model.finance;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.finance.logEntry.LogEntry;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<LogEntry> PREDICATE_SHOW_ALL_LOG_ENTRIES = unused -> true;

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

    /**
     * Returns true if an entry with the same identity as {@code LogEntry} exists in the address book.
     */
    boolean hasLogEntry(LogEntry logEntry);

    /**
     * Deletes the given log entry.
     * The entry must exist in the finance log.
     */
    void deleteLogEntry(LogEntry target);

    /**
     * Adds the given log entry.
     */
    void addLogEntry(LogEntry logEntry);

    /**
     * Replaces the given log entry {@code target} with {@code editedLogEntry}.
     * {@code target} must exist in the finance log.
     */
    void setLogEntry(LogEntry target, LogEntry editedLogEntry);

    /** Returns an unmodifiable view of the filtered list of log entries */
    ObservableList<LogEntry> getFilteredLogEntryList();

    /**
     * Updates the filter of the filtered list of log entries to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLogEntryList(Predicate<LogEntry> predicate);
}
