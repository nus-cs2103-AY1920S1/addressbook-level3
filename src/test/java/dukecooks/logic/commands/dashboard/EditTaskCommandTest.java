package dukecooks.logic.commands.dashboard;

import static dukecooks.testutil.dashboard.TypicalDashboard.getTypicalDashboardRecords;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.dashboard.DashboardRecords;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.dashboard.EditDashboardDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {

    private Model model = new ModelManager(getTypicalDashboardRecords(), new UserPrefs());

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTaskCommand editTaskCommand = new EditTaskCommand(TypicalIndexes.INDEX_FIRST_DASHBOARD,
                new EditTaskCommand.EditTaskDescriptor());
        Dashboard editedDashboard = model.getFilteredDashboardList()
                .get(TypicalIndexes.INDEX_FIRST_DASHBOARD.getZeroBased());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedDashboard);

        Model expectedModel = new ModelManager(new DashboardRecords(model.getDashboardRecords()), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_duplicateDashboardUnfilteredList_failure() {
        Dashboard firstDashboard = model.getFilteredDashboardList()
                .get(TypicalIndexes.INDEX_FIRST_DASHBOARD.getZeroBased());
        EditTaskCommand.EditTaskDescriptor descriptor = new EditDashboardDescriptorBuilder(firstDashboard).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(TypicalIndexes.INDEX_SECOND_DASHBOARD, descriptor);

        CommandTestUtil.assertRecipeCommandFailure(editTaskCommand, model,
                EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(TypicalIndexes.INDEX_FIRST_DASHBOARD,
                CommandTestUtil.DESC_HW);

        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor copyDescriptor = new EditTaskCommand
                .EditTaskDescriptor(CommandTestUtil.DESC_HW);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(TypicalIndexes.INDEX_FIRST_DASHBOARD,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(TypicalIndexes.INDEX_SECOND_DASHBOARD,
                CommandTestUtil.DESC_PLAY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(TypicalIndexes.INDEX_FIRST_DASHBOARD,
                CommandTestUtil.DESC_PLAY)));
    }

}
