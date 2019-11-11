package seedu.address.model.finance;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.finance.budget.Budget;
import seedu.address.model.finance.budget.BudgetList;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.LogEntryList;


/**
 * Wraps all data at the Modulo's finance component
 */
public class FinanceLog implements ReadOnlyFinanceLog {

    private final LogEntryList logEntries;
    private final BudgetList budgets;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        logEntries = new LogEntryList();
        budgets = new BudgetList();
    }

    public FinanceLog() {}

    /**
     * Creates an FinanceLog using the LogEntries in the {@code toBeCopied}
     */
    public FinanceLog(ReadOnlyFinanceLog toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the list of log entries with {@code logEntries}.
     */
    public void setLogEntries(List<LogEntry> logEntries) {
        this.logEntries.setLogEntries(logEntries);
    }

    /**
     * Replaces the contents of the list of budgets with {@code Budgets}.
     */
    public void setBudgets(List<Budget> logEntries) {
        this.budgets.setBudgets(logEntries);
    }

    /**
     * Resets the existing data of this {@code FinanceLog} with {@code newData}.
     */
    public void resetData(ReadOnlyFinanceLog newData) {
        requireNonNull(newData);
        setLogEntries(newData.getLogEntryList());
        setBudgets(newData.getBudgetList());
    }

    //// log entry-level operations

    /**
     * Returns true if a log entry with the same information as {@code logEntry} exists in the finance log.
     */
    public boolean hasLogEntry(LogEntry logEntry) {
        requireNonNull(logEntry);
        return logEntries.contains(logEntry);
    }

    /**
     * Adds a log entry to the finance log.
     */
    public void addLogEntry(LogEntry le) {
        logEntries.add(le);
    }

    /**
     * Replaces the given log entry {@code target} in the list with {@code editedLogEntry}.
     * {@code target} must exist in the finance log.
     */
    public void setLogEntry(LogEntry target, LogEntry editedLogEntry) {
        requireNonNull(editedLogEntry);
        logEntries.setLogEntry(target, editedLogEntry);
    }

    /**
     * Removes {@code key} from this {@code FinanceLog}.
     * {@code key} must exist in the finance log.
     */
    public void removeLogEntry(LogEntry key) {
        logEntries.remove(key);
    }

    /**
     * Set a log entry as repaid
     * @param logEntry should be of type either borrow or lend
     */
    public void markLogEntryAsRepaid(LogEntry logEntry) {
        logEntries.markAsRepaid(logEntry);
    }

    //// budget entry-level operations

    /**
     * Returns true if a budget with the same information as {@code Budget} exists in the finance log.
     */
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return budgets.contains(budget);
    }

    /**
     * Adds a budget to the finance log.
     */
    public void addBudget(Budget b) {
        budgets.add(b);
    }

    /**
     * Removes {@code key} from this {@code FinanceLog}.
     * {@code key} must exist in the finance log.
     */
    public void removeBudget(Budget key) {
        budgets.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        int numLogEntries = logEntries.asUnmodifiableObservableList().size();
        return numLogEntries == 1 ? " log entry" : " log entries";
    }

    @Override
    public ObservableList<LogEntry> getLogEntryList() {
        return logEntries.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Budget> getBudgetList() {
        return budgets.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinanceLog // instanceof handles nulls
                && logEntries.equals(((FinanceLog) other).logEntries)
                && budgets.equals(((FinanceLog) other).budgets));
    }

    @Override
    public int hashCode() {
        return Objects.hash(logEntries, budgets);
    }
}
