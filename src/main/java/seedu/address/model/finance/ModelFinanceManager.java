package seedu.address.model.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.finance.budget.Budget;
import seedu.address.model.finance.budget.BudgetData;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * Represents the in-memory model of the finance log data.
 */
public class ModelFinanceManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelFinanceManager.class);

    private final FinanceLog financeLog;
    private final UserPrefs userPrefs;
    private final FilteredList<LogEntry> filteredLogEntries;
    private final FilteredList<Budget> filteredBudgets;
    private GraphicsData graphicsData;

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
        filteredBudgets = new FilteredList<>(this.financeLog.getBudgetList());
        graphicsData = new GraphicsData();
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

    @Override
    public void setGraphicsData(GraphicsData gData) {
        requireNonNull(gData);
        this.graphicsData = gData;
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
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return financeLog.hasBudget(budget);
    }

    @Override
    public void deleteLogEntry(LogEntry target) {
        financeLog.removeLogEntry(target);
    }

    @Override
    public void deleteBudget(Budget target) {
        financeLog.removeBudget(target);
    }

    @Override
    public void addLogEntry(LogEntry logEntry) {
        financeLog.addLogEntry(logEntry);
        updateFilteredLogEntryList(PREDICATE_SHOW_ALL_LOG_ENTRIES);
    }

    @Override
    public void addBudget(Budget budget) {
        financeLog.addBudget(budget);
        updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
    }

    @Override
    public void setLogEntry(LogEntry target, LogEntry editedLogEntry) {
        requireAllNonNull(target, editedLogEntry);
        financeLog.setLogEntry(target, editedLogEntry);
    }

    @Override
    public void markLogEntryAsRepaid(LogEntry logEntry) {
        financeLog.markLogEntryAsRepaid(logEntry);
    }

    //=========== Filtered List of Finance Log Entries Accessors ===========

    /**
     * Returns an unmodifiable view of the list of {@code LogEntry} backed by the internal list of
     * {@code versionedFinanceLog}
     */
    @Override
    public ObservableList<LogEntry> getFilteredLogEntryList() {
        return filteredLogEntries;
    }

    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return filteredBudgets;
    }

    @Override
    public ObservableList<BudgetData> getFilteredBudgetDataList() {
        return filteredBudgets
                .stream()
                .map(b -> new BudgetData(b, getFilteredLogEntryList()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public boolean hasAnyActiveBudgetExceeded() {
        return getFilteredBudgetDataList()
                .stream()
                .filter(bd -> bd.isActive())
                .anyMatch(bd -> bd.hasExceeded());
    }

    @Override
    public boolean hasAnyActiveBudgetCloseToExceed() {
        return getFilteredBudgetDataList()
                .stream()
                .filter(bd -> bd.isActive())
                .anyMatch(bd -> bd.isCloseToExceed());
    }

    /**
     * Returns an a {@code GraphicsData} object
     */
    @Override
    public GraphicsData getGraphicsData() {
        return graphicsData;
    }

    @Override
    public void updateFilteredLogEntryList(Predicate<LogEntry> predicate) {
        requireNonNull(predicate);
        filteredLogEntries.setPredicate(predicate);
    }

    @Override
    public void updateFilteredBudgetList(Predicate<Budget> predicate) {
        requireNonNull(predicate);
        filteredBudgets.setPredicate(predicate);
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
