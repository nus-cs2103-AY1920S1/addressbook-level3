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
import seedu.eatme.testutil.EateryBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code CloseCommand}.
 */
public class CloseCommandTest {

    private Model model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Eatery closedEatery = new EateryBuilder().withTags("fastfood").withIsOpen(false).build();
        CloseCommand closeCommand = new CloseCommand(INDEX_FIRST_EATERY);

        String expectedMessage = String.format(CloseCommand.MESSAGE_CLOSED_EATERY_SUCCESS,
                closedEatery.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
        expectedModel.setEatery(model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased()), closedEatery);

        assertCommandSuccess(closeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEateryList().size() + 1);
        CloseCommand closeCommand = new CloseCommand(outOfBoundIndex);

        assertCommandFailure(closeCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);

        model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased());
        Eatery closedEatery = new EateryBuilder().withTags("fastfood").withIsOpen(false).build();
        CloseCommand closeCommand = new CloseCommand(INDEX_FIRST_EATERY);

        String expectedMessage = String.format(CloseCommand.MESSAGE_CLOSED_EATERY_SUCCESS,
                closedEatery.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
        expectedModel.setEatery(model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased()), closedEatery);

        assertCommandSuccess(closeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);

        Index outOfBoundIndex = INDEX_SECOND_EATERY;
        // ensures that outOfBoundIndex is still in bounds of eatery list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEateryList().getEateryList().size());

        CloseCommand closeCommand = new CloseCommand(outOfBoundIndex);

        assertCommandFailure(closeCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CloseCommand closeFirstCommand = new CloseCommand(INDEX_FIRST_EATERY);
        CloseCommand closeSecondCommand = new CloseCommand(INDEX_SECOND_EATERY);

        // same object -> returns true
        assertTrue(closeFirstCommand.equals(closeFirstCommand));

        // same values -> returns true
        CloseCommand closeFirstCommandCopy = new CloseCommand(INDEX_FIRST_EATERY);
        assertTrue(closeFirstCommand.equals(closeFirstCommandCopy));

        // different types -> returns false
        assertFalse(closeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(closeFirstCommand.equals(null));

        // different eatery -> returns false
        assertFalse(closeFirstCommand.equals(closeSecondCommand));
    }
}
