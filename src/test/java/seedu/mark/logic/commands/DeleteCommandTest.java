package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.CommandTestUtil.showBookmarkAtIndex;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;
import static seedu.mark.testutil.TypicalIndexes.INDEX_SECOND_BOOKMARK;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.storage.StorageStub;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Bookmark bookmarkToDelete = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_BOOKMARK);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_BOOKMARK_SUCCESS, bookmarkToDelete);

        ModelManager expectedModel = new ModelManager(model.getMark(), new UserPrefs());
        expectedModel.deleteBookmark(bookmarkToDelete);
        expectedModel.saveMark();

        assertCommandSuccess(deleteCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, new StorageStub(),
                Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);

        Bookmark bookmarkToDelete = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_BOOKMARK);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_BOOKMARK_SUCCESS, bookmarkToDelete);

        Model expectedModel = new ModelManager(model.getMark(), new UserPrefs());
        expectedModel.deleteBookmark(bookmarkToDelete);
        expectedModel.saveMark();
        showNoBookmark(expectedModel);

        assertCommandSuccess(deleteCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);

        Index outOfBoundIndex = INDEX_SECOND_BOOKMARK;
        // ensures that outOfBoundIndex is still in bounds of bookmark list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMark().getBookmarkList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, new StorageStub(),
                Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_BOOKMARK);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_BOOKMARK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_BOOKMARK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different bookmark -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBookmark(Model model) {
        model.updateFilteredBookmarkList(p -> false);

        assertTrue(model.getFilteredBookmarkList().isEmpty());
    }
}
