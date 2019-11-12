package seedu.eatme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.logic.commands.CommandTestUtil.showEateryAtIndex;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_FIRST_EATERY;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_SECOND_EATERY;

import org.junit.jupiter.api.Test;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;
import seedu.eatme.model.eatery.Eatery;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Eatery eateryToDelete = model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_EATERY);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EATERY_SUCCESS,
                eateryToDelete.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
        expectedModel.deleteEatery(eateryToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEateryList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);

        Eatery eateryToDelete = model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_EATERY);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EATERY_SUCCESS,
                eateryToDelete.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
        expectedModel.deleteEatery(eateryToDelete);
        showNoEatery(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);

        Index outOfBoundIndex = INDEX_SECOND_EATERY;
        // ensures that outOfBoundIndex is still in bounds of eatery list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEateryList().getEateryList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_EATERY);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_EATERY);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_EATERY);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different eatery -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEatery(Model model) {
        model.updateFilteredEateryList(p -> false);

        assertTrue(model.getFilteredEateryList().isEmpty());
    }
}
