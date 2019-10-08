package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudyPlanAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDYPLAN;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDYPLAN;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.storage.DeleteCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalModulePlanner(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        StudyPlan studyPlanToDelete = model.getFilteredStudyPlanList().get(INDEX_FIRST_STUDYPLAN.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_STUDYPLAN);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDYPLAN_SUCCESS, studyPlanToDelete);

        ModelManager expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs());
        expectedModel.deleteStudyPlan(studyPlanToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudyPlanList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_STUDYPLAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStudyPlanAtIndex(model, INDEX_FIRST_STUDYPLAN);

        StudyPlan studyPlanToDelete = model.getFilteredStudyPlanList().get(INDEX_FIRST_STUDYPLAN.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_STUDYPLAN);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDYPLAN_SUCCESS, studyPlanToDelete);

        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs());
        expectedModel.deleteStudyPlan(studyPlanToDelete);
        showNoStudyPlan(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStudyPlanAtIndex(model, INDEX_FIRST_STUDYPLAN);

        Index outOfBoundIndex = INDEX_SECOND_STUDYPLAN;
        // ensures that outOfBoundIndex is still in bounds of module planner list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getModulePlanner().getStudyPlanList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_STUDYPLAN_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_STUDYPLAN);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_STUDYPLAN);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_STUDYPLAN);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different studyPlan -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStudyPlan(Model model) {
        model.updateFilteredStudyPlanList(p -> false);

        assertTrue(model.getFilteredStudyPlanList().isEmpty());
    }
}
