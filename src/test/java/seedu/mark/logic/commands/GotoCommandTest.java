package seedu.mark.logic.commands;

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
import seedu.mark.model.bookmark.Url;

public class GotoCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Bookmark bookmarkToOpen = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        GotoCommand gotoCommand = new GotoCommand(INDEX_FIRST_BOOKMARK);

        String expectedMessage = String.format(GotoCommand.MESSAGE_GOTO_BOOKMARK_ACKNOWLEDGEMENT, bookmarkToOpen);
        Url expectedUrl = bookmarkToOpen.getUrl();
        CommandResult expectedCommandResult = new GotoCommandResult(expectedMessage, expectedUrl);

        assertCommandSuccess(gotoCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkList().size() + 1);
        GotoCommand gotoCommand = new GotoCommand(outOfBoundIndex);

        assertCommandFailure(gotoCommand, model, Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);
        showBookmarkAtIndex(expectedModel, INDEX_FIRST_BOOKMARK);

        Bookmark bookmarkToOpen = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        GotoCommand gotoCommand = new GotoCommand(INDEX_FIRST_BOOKMARK);

        String expectedMessage = String.format(GotoCommand.MESSAGE_GOTO_BOOKMARK_ACKNOWLEDGEMENT, bookmarkToOpen);
        Url expectedUrl = bookmarkToOpen.getUrl();
        CommandResult expectedCommandResult = new GotoCommandResult(expectedMessage, expectedUrl);

        assertCommandSuccess(gotoCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);

        Index outOfBoundIndex = INDEX_SECOND_BOOKMARK;
        // ensures that outOfBoundIndex is still in bounds of bookmark list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMark().getBookmarkList().size());

        GotoCommand gotoCommand = new GotoCommand(outOfBoundIndex);

        assertCommandFailure(gotoCommand, model, Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }
}
