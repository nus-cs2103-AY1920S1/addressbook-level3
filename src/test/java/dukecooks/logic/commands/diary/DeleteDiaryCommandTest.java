package dukecooks.logic.commands.diary;

import static dukecooks.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.diary.components.Diary;
import dukecooks.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteDiaryCommandTest {

    private Model model = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Diary diaryToDelete = model.getFilteredDiaryList().get(TypicalIndexes.INDEX_FIRST_DIARY.getZeroBased());
        DeleteDiaryCommand deleteCommand = new DeleteDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY);

        String expectedMessage = String.format(DeleteDiaryCommand.MESSAGE_DELETE_DIARY_SUCCESS, diaryToDelete);

        ModelManager expectedModel = new ModelManager(model.getDiaryRecords(), new UserPrefs());
        expectedModel.deleteDiary(diaryToDelete);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDiaryList().size() + 1);
        DeleteDiaryCommand deleteCommand = new DeleteDiaryCommand(outOfBoundIndex);

        CommandTestUtil.assertDiaryCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showDiaryAtIndex(model, TypicalIndexes.INDEX_FIRST_DIARY);

        Diary diaryToDelete = model.getFilteredDiaryList().get(TypicalIndexes.INDEX_FIRST_DIARY.getZeroBased());
        DeleteDiaryCommand deleteCommand = new DeleteDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY);

        String expectedMessage = String.format(DeleteDiaryCommand.MESSAGE_DELETE_DIARY_SUCCESS, diaryToDelete);

        Model expectedModel = new ModelManager(model.getDiaryRecords(), new UserPrefs());
        expectedModel.deleteDiary(diaryToDelete);
        showNoDiary(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showDiaryAtIndex(model, TypicalIndexes.INDEX_FIRST_DIARY);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_DIARY;
        // ensures that outOfBoundIndex is still in bounds of Duke Cooks list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDiaryRecords().getDiaryList().size());

        DeleteDiaryCommand deleteCommand = new DeleteDiaryCommand(outOfBoundIndex);

        CommandTestUtil.assertDiaryCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteDiaryCommand deleteFirstCommand = new DeleteDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY);
        DeleteDiaryCommand deleteSecondCommand = new DeleteDiaryCommand(TypicalIndexes.INDEX_SECOND_DIARY);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteDiaryCommand deleteFirstCommandCopy = new DeleteDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different diary -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no diary.
     */
    private void showNoDiary(Model model) {
        model.updateFilteredDiaryList(p -> false);

        assertTrue(model.getFilteredDiaryList().isEmpty());
    }
}
