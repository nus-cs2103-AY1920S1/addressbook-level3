package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEateryAtIndex;
import static seedu.address.testutil.TypicalEateries.getTypicalCloseAddressBook;
import static seedu.address.testutil.TypicalFeeds.getTypicalFeedList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EATERY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EATERY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.eatery.Eatery;
import seedu.address.testutil.EateryBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ReopenCommand}.
 */
public class ReopenCommandTest {

    private Model model = new ModelManager(getTypicalCloseAddressBook(), getTypicalFeedList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Eatery reopenedEatery = new EateryBuilder().withTags("friends").build();
        ReopenCommand reopenCommand = new ReopenCommand(INDEX_FIRST_EATERY);

        String expectedMessage = String.format(ReopenCommand.MESSAGE_REOPENED_EATERY_SUCCESS, reopenedEatery);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getFeedList(), new UserPrefs());
        expectedModel.setEatery(model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased()), reopenedEatery);

        assertCommandSuccess(reopenCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEateryList().size() + 1);
        ReopenCommand reopenCommand = new ReopenCommand(outOfBoundIndex);

        assertCommandFailure(reopenCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);

        model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased());
        Eatery reopenedEatery = new EateryBuilder().withTags("friends").build();
        ReopenCommand reopenCommand = new ReopenCommand(INDEX_FIRST_EATERY);

        String expectedMessage = String.format(ReopenCommand.MESSAGE_REOPENED_EATERY_SUCCESS, reopenedEatery);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getFeedList(), new UserPrefs());
        expectedModel.setEatery(model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased()), reopenedEatery);

        assertCommandSuccess(reopenCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);

        Index outOfBoundIndex = INDEX_SECOND_EATERY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEateryList().size());

        ReopenCommand reopenCommand = new ReopenCommand(outOfBoundIndex);

        assertCommandFailure(reopenCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ReopenCommand reopenFirstCommand = new ReopenCommand(INDEX_FIRST_EATERY);
        ReopenCommand reopenSecondCommand = new ReopenCommand(INDEX_SECOND_EATERY);

        // same object -> returns true
        assertTrue(reopenFirstCommand.equals(reopenFirstCommand));

        // same values -> returns true
        ReopenCommand reopenFirstCommandCopy = new ReopenCommand(INDEX_FIRST_EATERY);
        assertTrue(reopenFirstCommand.equals(reopenFirstCommandCopy));

        // different types -> returns false
        assertFalse(reopenFirstCommand.equals(1));

        // null -> returns false
        assertFalse(reopenFirstCommand.equals(null));

        // different eatery -> returns false
        assertFalse(reopenFirstCommand.equals(reopenSecondCommand));
    }
}
