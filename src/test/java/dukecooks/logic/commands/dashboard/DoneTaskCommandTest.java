package dukecooks.logic.commands.dashboard;

import static dukecooks.testutil.dashboard.TypicalDashboard.getTypicalDashboardRecords;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DoneTaskCommand}.
 */
public class DoneTaskCommandTest {

    private Model model = new ModelManager(getTypicalDashboardRecords(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDashboardList().size() + 1);
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(outOfBoundIndex);

        CommandTestUtil.assertDashboardCommandFailure(doneTaskCommand, model,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DoneTaskCommand doneFirstCommand = new DoneTaskCommand(TypicalIndexes.INDEX_FIRST_DASHBOARD);
        DoneTaskCommand doneSecondCommand = new DoneTaskCommand(TypicalIndexes.INDEX_SECOND_DASHBOARD);

        // same object -> returns true
        assertTrue(doneFirstCommand.equals(doneFirstCommand));

        // same values -> returns true
        DoneTaskCommand doneFirstCommandCopy = new DoneTaskCommand(TypicalIndexes.INDEX_FIRST_DASHBOARD);
        assertTrue(doneFirstCommand.equals(doneFirstCommandCopy));

        // different types -> returns false
        assertFalse(doneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(doneFirstCommand.equals(null));

        // different dashboard -> returns false
        assertFalse(doneFirstCommand.equals(doneSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDashboard(Model model) {
        model.updateFilteredDashboardList(p -> false);

        assertTrue(model.getFilteredDashboardList().isEmpty());
    }
}
