package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.GraphicsData;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.ReadOnlyFinanceLog;
import seedu.address.model.finance.ReadOnlyUserPrefs;
import seedu.address.model.finance.budget.Budget;
import seedu.address.model.finance.budget.BudgetData;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.testutil.LogEntryBuilder;

public class LendCommandTest {

    @Test
    public void constructor_nullLogEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LendCommand(null));
    }

    @Test
    public void execute_logEntryAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLogEntryAdded modelStub = new ModelStubAcceptingLogEntryAdded();
        LogEntry validLogEntry = new LogEntryBuilder().buildLend();

        CommandResult commandResult = new LendCommand(validLogEntry).execute(modelStub);

        assertEquals(String.format(LendCommand.MESSAGE_SUCCESS, validLogEntry), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validLogEntry), modelStub.logEntriesAdded);
    }

    @Test
    public void equals() {
        LogEntry log1 = new LogEntryBuilder().withDescription("Egg tart").buildLend();
        LogEntry log2 = new LogEntryBuilder().withDescription("Waffle").buildLend();
        LendCommand addLog1Command = new LendCommand(log1);
        LendCommand addLog2Command = new LendCommand(log2);

        // same object -> returns true
        assertTrue(addLog1Command.equals(addLog1Command));

        // same values -> returns true
        LendCommand addLog1CommandCopy = new LendCommand(log1);
        assertTrue(addLog1Command.equals(addLog1CommandCopy));

        // different types -> returns false
        assertFalse(addLog1Command.equals(1));

        // null -> returns false
        assertFalse(addLog1Command.equals(null));

        // different task -> returns false
        assertFalse(addLog1Command.equals(addLog2Command));
    }

    /**
     * A default Model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public Path getFinanceLogFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinanceLogFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        public void addLogEntry(LogEntry logEntry) {
            throw new AssertionError("This method should not be called.");
        }

        public void addBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinanceLog(ReadOnlyFinanceLog newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFinanceLog getFinanceLog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGraphicsData(GraphicsData gData) {
            throw new AssertionError("This method should not be called.");
        }

        public boolean hasLogEntry(LogEntry task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLogEntry(LogEntry target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLogEntry(LogEntry target, LogEntry editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markLogEntryAsRepaid(LogEntry logEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<LogEntry> getFilteredLogEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<BudgetData> getFilteredBudgetDataList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLogEntryList(Predicate<LogEntry> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBudgetList(Predicate<Budget> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        public void updateLogEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GraphicsData getGraphicsData() {
            throw new AssertionError("This method should not be called.");
        }

        public boolean hasAnyActiveBudgetExceeded() {
            throw new AssertionError("This method should not be called.");
        }

        public boolean hasAnyActiveBudgetCloseToExceed() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single log entry.
     */
    private class ModelStubWithLogEntry extends ModelStub {
        private final LogEntry logEntry;

        ModelStubWithLogEntry(LogEntry logEntry) {
            requireNonNull(logEntry);
            this.logEntry = logEntry;
        }

        @Override
        public boolean hasLogEntry(LogEntry logEntry) {
            requireNonNull(logEntry);
            return this.logEntry.isSameLogEntry(logEntry);
        }
    }

    /**
     * A Model stub that always accepts the log entry being added.
     */
    private class ModelStubAcceptingLogEntryAdded extends ModelStub {
        final ArrayList<LogEntry> logEntriesAdded = new ArrayList<>();

        @Override
        public boolean hasLogEntry(LogEntry logEntry) {
            requireNonNull(logEntry);
            return logEntriesAdded.stream().anyMatch(logEntry::isSameLogEntry);
        }

        @Override
        public void addLogEntry(LogEntry logEntry) {
            requireNonNull(logEntry);
            logEntriesAdded.add(logEntry);
        }

        @Override
        public ReadOnlyFinanceLog getFinanceLog() {
            return new FinanceLog();
        }
    }

}
