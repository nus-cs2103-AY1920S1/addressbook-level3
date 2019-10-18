package seedu.address.logic.commands.diary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDiaryAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DIARY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DIARY;
import static seedu.address.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.diary.components.Diary;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteDiaryCommandTest {

    private Model model = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Diary diaryToDelete = model.getFilteredDiaryList().get(INDEX_FIRST_DIARY.getZeroBased());
        DeleteDiaryCommand deleteCommand = new DeleteDiaryCommand(INDEX_FIRST_DIARY);

        String expectedMessage = String.format(DeleteDiaryCommand.MESSAGE_DELETE_DIARY_SUCCESS, diaryToDelete);

        ModelManager expectedModel = new ModelManager(model.getDiaryRecords(), new UserPrefs());
        expectedModel.deleteDiary(diaryToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDiaryList().size() + 1);
        DeleteDiaryCommand deleteCommand = new DeleteDiaryCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDiaryAtIndex(model, INDEX_FIRST_DIARY);

        Diary diaryToDelete = model.getFilteredDiaryList().get(INDEX_FIRST_DIARY.getZeroBased());
        DeleteDiaryCommand deleteCommand = new DeleteDiaryCommand(INDEX_FIRST_DIARY);

        String expectedMessage = String.format(DeleteDiaryCommand.MESSAGE_DELETE_DIARY_SUCCESS, diaryToDelete);

        Model expectedModel = new ModelManager(model.getDiaryRecords(), new UserPrefs());
        expectedModel.deleteDiary(diaryToDelete);
        showNoDiary(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDiaryAtIndex(model, INDEX_FIRST_DIARY);

        Index outOfBoundIndex = INDEX_SECOND_DIARY;
        // ensures that outOfBoundIndex is still in bounds of Duke Cooks list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDiaryRecords().getDiaryList().size());

        DeleteDiaryCommand deleteCommand = new DeleteDiaryCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteDiaryCommand deleteFirstCommand = new DeleteDiaryCommand(INDEX_FIRST_DIARY);
        DeleteDiaryCommand deleteSecondCommand = new DeleteDiaryCommand(INDEX_SECOND_DIARY);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteDiaryCommand deleteFirstCommandCopy = new DeleteDiaryCommand(INDEX_FIRST_DIARY);
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
