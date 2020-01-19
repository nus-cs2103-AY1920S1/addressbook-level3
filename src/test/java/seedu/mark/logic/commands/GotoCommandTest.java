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
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.GotoCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.storage.Storage;
import seedu.mark.storage.StorageStub;

public class GotoCommandTest {

    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());
    private Storage storage = new StorageStub();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Bookmark bookmarkToOpen = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        GotoCommand gotoCommand = new GotoCommand(INDEX_FIRST_BOOKMARK);

        expectedModel.setCurrentUrl(bookmarkToOpen.getUrl());
        String expectedMessage = String.format(GotoCommand.MESSAGE_GOTO_BOOKMARK_ACKNOWLEDGEMENT, bookmarkToOpen);
        CommandResult expectedCommandResult = new GotoCommandResult(expectedMessage);

        assertCommandSuccess(gotoCommand, model, storage, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkList().size() + 1);
        GotoCommand gotoCommand = new GotoCommand(outOfBoundIndex);

        assertCommandFailure(gotoCommand, model, storage, Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);
        showBookmarkAtIndex(expectedModel, INDEX_FIRST_BOOKMARK);

        Bookmark bookmarkToOpen = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        GotoCommand gotoCommand = new GotoCommand(INDEX_FIRST_BOOKMARK);

        expectedModel.setCurrentUrl(bookmarkToOpen.getUrl());
        String expectedMessage = String.format(GotoCommand.MESSAGE_GOTO_BOOKMARK_ACKNOWLEDGEMENT, bookmarkToOpen);
        CommandResult expectedCommandResult = new GotoCommandResult(expectedMessage);

        assertCommandSuccess(gotoCommand, model, storage, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);

        Index outOfBoundIndex = INDEX_SECOND_BOOKMARK;
        // ensures that outOfBoundIndex is still in bounds of bookmark list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMark().getBookmarkList().size());

        GotoCommand gotoCommand = new GotoCommand(outOfBoundIndex);

        assertCommandFailure(gotoCommand, model, storage, Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        GotoCommand gotoFirstCommand = new GotoCommand(INDEX_FIRST_BOOKMARK);
        GotoCommand gotoSecondCommand = new GotoCommand(INDEX_SECOND_BOOKMARK);

        // same object -> returns true
        assertTrue(gotoFirstCommand.equals(gotoFirstCommand));

        // same values -> returns true
        GotoCommand gotoFirstCommandCopy = new GotoCommand(INDEX_FIRST_BOOKMARK);
        assertTrue(gotoFirstCommand.equals(gotoFirstCommandCopy));

        // different types -> returns false
        assertFalse(gotoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(gotoFirstCommand.equals(null));

        // different bookmark -> returns false
        assertFalse(gotoFirstCommand.equals(gotoSecondCommand));
    }
}
