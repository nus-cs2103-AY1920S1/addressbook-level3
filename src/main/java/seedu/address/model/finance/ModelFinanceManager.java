package seedu.address.model.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.finance.logEntry.LogEntry;


/**
 * Represents the in-memory model of the finance log data.
 */
public class ModelFinanceManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelFinanceManager.class);

    private final FinanceLog financeLog;
    private final UserPrefs userPrefs;
    private final FilteredList<LogEntry> filteredLogEntries;

    /**
     * Initializes a ModelManager with the given financeLog and userPrefs.
     */
    public ModelFinanceManager(ReadOnlyFinanceLog financeLog, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(financeLog, userPrefs);

        logger.fine("Initializing with finance log: " + financeLog + " and user prefs " + userPrefs);

        this.financeLog = new FinanceLog(financeLog);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredLogEntries = new FilteredList<>(this.financeLog.getLogEntryList());
    }

    public ModelFinanceManager() {
        this(new FinanceLog(), new UserPrefs());
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
    public Path getFinanceLogFilePath() {
        return userPrefs.getFinanceLogFilePath();
    }

    @Override
    public void setFinanceLogFilePath(Path financeLogFilePath) {
        requireNonNull(financeLogFilePath);
        userPrefs.setFinanceLogFilePath(financeLogFilePath);
    }

    //=========== FinanceLog ================================================================================

    @Override
    public void setFinanceLog(ReadOnlyFinanceLog financeLog) {
        this.financeLog.resetData(financeLog);
    }

    @Override
    public ReadOnlyFinanceLog getFinanceLog() {
        return financeLog;
    }

    @Override
    public boolean hasLogEntry(LogEntry logEntry) {
        requireNonNull(logEntry);
        return financeLog.hasLogEntry(logEntry);
    }

    @Override
    public void deleteLogEntry(LogEntry target) {
        financeLog.removeLogEntry(target);
    }

    @Override
    public void addLogEntry(LogEntry logEntry) {
        financeLog.addLogEntry(logEntry);
        updateFilteredLogEntryList(PREDICATE_SHOW_ALL_LOG_ENTRIES);
    }

    @Override
    public void setLogEntry(LogEntry target, LogEntry editedLogEntry) {
        requireAllNonNull(target, editedLogEntry);

        financeLog.setLogEntry(target, editedLogEntry);
    }

    //=========== Filtered List of Finance Log Entries Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code LogEntry} backed by the internal list of
     * {@code versionedFinanceLog}
     */
    @Override
    public ObservableList<LogEntry> getFilteredLogEntryList() {
        return filteredLogEntries;
    }

    @Override
    public void updateFilteredLogEntryList(Predicate<LogEntry> predicate) {
        requireNonNull(predicate);
        filteredLogEntries.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelFinanceManager)) {
            return false;
        }

        // state check
        ModelFinanceManager other = (ModelFinanceManager) obj;
        return financeLog.equals(other.financeLog)
                && userPrefs.equals(other.userPrefs)
                && filteredLogEntries.equals(other.filteredLogEntries);
    }

}
