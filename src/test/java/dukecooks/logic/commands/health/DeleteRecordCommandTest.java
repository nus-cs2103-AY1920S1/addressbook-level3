package dukecooks.logic.commands.health;

import static dukecooks.testutil.health.TypicalRecords.getTypicalHealthRecords;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.health.components.Record;
import dukecooks.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteRecordCommand}.
 */
public class DeleteRecordCommandTest {

    private Model model = new ModelManager(getTypicalHealthRecords(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Record recordToDelete = model.getFilteredRecordList().get(TypicalIndexes.INDEX_FIRST_RECORD.getZeroBased());
        DeleteRecordCommand deleteRecordCommand = new DeleteRecordCommand(TypicalIndexes.INDEX_FIRST_RECORD);

        String expectedMessage = String.format(DeleteRecordCommand.MESSAGE_DELETE_RECORD_SUCCESS, recordToDelete);

        ModelManager expectedModel = new ModelManager(model.getHealthRecords(), new UserPrefs());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.updateFilteredRecordList(x -> x.getType().equals(recordToDelete.getType()));

        CommandTestUtil.assertCommandSuccess(deleteRecordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        DeleteRecordCommand deleteRecordCommand = new DeleteRecordCommand(outOfBoundIndex);

        CommandTestUtil.assertRecordCommandFailure(deleteRecordCommand, model,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Record recordToDelete = model.getFilteredRecordList().get(TypicalIndexes.INDEX_FIRST_RECORD.getZeroBased());
        DeleteRecordCommand deleteRecordCommand = new DeleteRecordCommand(TypicalIndexes.INDEX_FIRST_RECORD);

        String expectedMessage = String.format(DeleteRecordCommand.MESSAGE_DELETE_RECORD_SUCCESS, recordToDelete);

        Model expectedModel = new ModelManager(model.getHealthRecords(), new UserPrefs());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.updateFilteredRecordList(x -> x.getType().equals(recordToDelete.getType()));

        CommandTestUtil.assertCommandSuccess(deleteRecordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteRecordCommand deleteFirstCommand = new DeleteRecordCommand(TypicalIndexes.INDEX_FIRST_RECORD);
        DeleteRecordCommand deleteSecondCommand = new DeleteRecordCommand(TypicalIndexes.INDEX_SECOND_RECORD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteRecordCommand deleteFirstCommandCopy = new DeleteRecordCommand(TypicalIndexes.INDEX_FIRST_RECORD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different record -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoRecord(Model model) {
        model.updateFilteredRecordList(p -> false);

        assertTrue(model.getFilteredRecordList().isEmpty());
    }
}
