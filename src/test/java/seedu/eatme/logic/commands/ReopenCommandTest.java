package seedu.eatme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.logic.commands.CommandTestUtil.showEateryAtIndex;
import static seedu.eatme.testutil.TypicalEateries.getTypicalCloseEateryList;
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
import seedu.eatme.testutil.EateryBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ReopenCommand}.
 */
public class ReopenCommandTest {

    private Model model = new ModelManager(getTypicalCloseEateryList(), getTypicalFeedList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Eatery reopenedEatery = new EateryBuilder().withTags("fastfood").build();
        ReopenCommand reopenCommand = new ReopenCommand(INDEX_FIRST_EATERY);

        String expectedMessage = String.format(ReopenCommand.MESSAGE_REOPENED_EATERY_SUCCESS,
                reopenedEatery.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
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
        Eatery reopenedEatery = new EateryBuilder().withTags("fastfood").build();
        ReopenCommand reopenCommand = new ReopenCommand(INDEX_FIRST_EATERY);

        String expectedMessage = String.format(ReopenCommand.MESSAGE_REOPENED_EATERY_SUCCESS,
                reopenedEatery.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
        expectedModel.setEatery(model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased()), reopenedEatery);

        assertCommandSuccess(reopenCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);

        Index outOfBoundIndex = INDEX_SECOND_EATERY;
        // ensures that outOfBoundIndex is still in bounds of eatery list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEateryList().getEateryList().size());

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
