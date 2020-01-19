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
 * {@code FavoriteCommand}.
 */
public class FavoriteCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Bookmark bookmarkToFavorite = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        FavoriteCommand favoriteCommand = new FavoriteCommand(INDEX_FIRST_BOOKMARK);

        String expectedMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_BOOKMARK_SUCCESS, bookmarkToFavorite);

        ModelManager expectedModel = new ModelManager(model.getMark(), new UserPrefs());
        expectedModel.favoriteBookmark(bookmarkToFavorite);
        expectedModel.saveMark(expectedMessage);

        assertCommandSuccess(favoriteCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkList().size() + 1);
        FavoriteCommand favoriteCommand = new FavoriteCommand(outOfBoundIndex);

        assertCommandFailure(favoriteCommand, model, new StorageStub(),
                Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);

        Bookmark bookmarkToFavorite = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        FavoriteCommand favoriteCommand = new FavoriteCommand(INDEX_FIRST_BOOKMARK);

        String expectedMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_BOOKMARK_SUCCESS, bookmarkToFavorite);

        Model expectedModel = new ModelManager(model.getMark(), new UserPrefs());
        expectedModel.favoriteBookmark(bookmarkToFavorite);
        expectedModel.saveMark(expectedMessage);
        showBookmarkAtIndex(expectedModel, INDEX_FIRST_BOOKMARK);

        assertCommandSuccess(favoriteCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);

        Index outOfBoundIndex = INDEX_SECOND_BOOKMARK;
        // ensures that outOfBoundIndex is still in bounds of bookmark list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMark().getBookmarkList().size());

        FavoriteCommand favoriteCommand = new FavoriteCommand(outOfBoundIndex);

        assertCommandFailure(favoriteCommand, model, new StorageStub(),
                Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FavoriteCommand favoriteFirstCommand = new FavoriteCommand(INDEX_FIRST_BOOKMARK);
        FavoriteCommand favoriteSecondCommand = new FavoriteCommand(INDEX_SECOND_BOOKMARK);

        // same object -> returns true
        assertTrue(favoriteFirstCommand.equals(favoriteFirstCommand));

        // same values -> returns true
        FavoriteCommand favoriteFirstCommandCopy = new FavoriteCommand(INDEX_FIRST_BOOKMARK);
        assertTrue(favoriteFirstCommand.equals(favoriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favoriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favoriteFirstCommand.equals(null));

        // different bookmark -> returns false
        assertFalse(favoriteFirstCommand.equals(favoriteSecondCommand));
    }
}
