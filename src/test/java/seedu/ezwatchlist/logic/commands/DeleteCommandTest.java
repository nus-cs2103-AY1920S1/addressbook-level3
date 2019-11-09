package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.showShowAtIndex;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_SECOND_SHOW;
import static seedu.ezwatchlist.testutil.TypicalShows.getDatabase;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.commons.core.messages.Messages;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.show.Show;
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalWatchList(), getDatabase(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Show showToDelete = model.getFilteredShowList().get(INDEX_FIRST_SHOW.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SHOW);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SHOW_SUCCESS, showToDelete);

        ModelManager expectedModel = new ModelManager(model.getWatchList(), getDatabase(), new UserPrefs());
        expectedModel.deleteShow(showToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredShowList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showShowAtIndex(model, INDEX_FIRST_SHOW);

        Show showToDelete = model.getFilteredShowList().get(INDEX_FIRST_SHOW.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SHOW);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SHOW_SUCCESS, showToDelete);

        Model expectedModel = new ModelManager(model.getWatchList(), getDatabase(), new UserPrefs());
        expectedModel.deleteShow(showToDelete);
        showNoShow(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showShowAtIndex(model, INDEX_FIRST_SHOW);

        Index outOfBoundIndex = INDEX_SECOND_SHOW;
        // ensures that outOfBoundIndex is still in bounds of watch list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWatchList().getShowList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_SHOW);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_SHOW);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_SHOW);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different show -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoShow(Model model) {
        model.updateFilteredShowList(p -> false);

        assertTrue(model.getFilteredShowList().isEmpty());
    }
}
