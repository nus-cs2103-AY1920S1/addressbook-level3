package seedu.address.logic.finance.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.finance.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_LOGENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SEVENTH_LOGENTRY;
import static seedu.address.testutil.TypicalLogEntries.getTypicalFinanceLog;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.ModelFinanceManager;
import seedu.address.model.finance.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RepaidCommand}.
 */
public class RepaidCommandTest {

    private Model financeModel =
        new ModelFinanceManager(getTypicalFinanceLog(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(financeModel.getFilteredLogEntryList().size() + 1);
        RepaidCommand repaidCommand = new RepaidCommand(outOfBoundIndex);

        assertCommandFailure(repaidCommand, financeModel, Messages.MESSAGE_INVALID_LOG_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        RepaidCommand repaidFirstCommand = new RepaidCommand(INDEX_FIFTH_LOGENTRY);
        RepaidCommand repaidSecondCommand = new RepaidCommand(INDEX_SEVENTH_LOGENTRY);

        // same object -> returns true
        assertTrue(repaidFirstCommand.equals(repaidFirstCommand));

        // same values -> returns true
        RepaidCommand repaidFirstCommandCopy = new RepaidCommand(INDEX_FIFTH_LOGENTRY);
        assertTrue(repaidFirstCommand.equals(repaidFirstCommandCopy));

        // different types -> returns false
        assertFalse(repaidFirstCommand.equals(1));

        // null -> returns false
        assertFalse(repaidFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(repaidFirstCommand.equals(repaidSecondCommand));
    }

    /**
     * Updates {@code financeModel}'s filtered list to show no entries.
     */
    private void showNoLogEntries(Model financeModel) {
        financeModel.updateFilteredLogEntryList(p -> false);
        assertTrue(financeModel.getFilteredLogEntryList().isEmpty());
    }
}
