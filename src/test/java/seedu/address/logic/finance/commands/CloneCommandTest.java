package seedu.address.logic.finance.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.finance.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LOGENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LOGENTRY;
import static seedu.address.testutil.TypicalLogEntries.getTypicalFinanceLog;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.ModelFinanceManager;
import seedu.address.model.finance.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code CloneCommand}.
 */
public class CloneCommandTest {

    private Model financeModel =
        new ModelFinanceManager(getTypicalFinanceLog(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(financeModel.getFilteredLogEntryList().size() + 1);
        CloneCommand cloneCommand = new CloneCommand(outOfBoundIndex);

        assertCommandFailure(cloneCommand, financeModel, Messages.MESSAGE_INVALID_LOG_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CloneCommand cloneFirstCommand = new CloneCommand(INDEX_FIRST_LOGENTRY);
        CloneCommand cloneSecondCommand = new CloneCommand(INDEX_SECOND_LOGENTRY);

        // same object -> returns true
        assertTrue(cloneFirstCommand.equals(cloneFirstCommand));

        // same values -> returns true
        CloneCommand cloneFirstCommandCopy = new CloneCommand(INDEX_FIRST_LOGENTRY);
        assertTrue(cloneFirstCommand.equals(cloneFirstCommandCopy));

        // different types -> returns false
        assertFalse(cloneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(cloneFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(cloneFirstCommand.equals(cloneSecondCommand));
    }

}
