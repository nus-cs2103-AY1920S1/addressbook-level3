package seedu.address.logic.finance.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.finance.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.finance.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LOGENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LOGENTRY;
import static seedu.address.testutil.TypicalLogEntries.getTypicalFinanceLog;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.ModelFinanceManager;
import seedu.address.model.finance.UserPrefs;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model financeModel =
        new ModelFinanceManager(getTypicalFinanceLog(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        LogEntry logEntryToDelete = financeModel.getFilteredLogEntryList().get(INDEX_FIRST_LOGENTRY.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOGENTRY);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LOG_ENTRY_SUCCESS, logEntryToDelete);

        ModelFinanceManager expectedModel =
            new ModelFinanceManager(financeModel.getFinanceLog(), new UserPrefs());
        expectedModel.deleteLogEntry(logEntryToDelete);

        assertCommandSuccess(deleteCommand, financeModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(financeModel.getFilteredLogEntryList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, financeModel, Messages.MESSAGE_INVALID_LOG_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_LOGENTRY);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_LOGENTRY);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_LOGENTRY);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code financeModel}'s filtered list to show no entries.
     */
    private void showNoLogEntries(Model financeModel) {
        financeModel.updateFilteredLogEntryList(p -> false);
        assertTrue(financeModel.getFilteredLogEntryList().isEmpty());
    }
}
