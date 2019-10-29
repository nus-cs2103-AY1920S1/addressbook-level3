package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEateries.getTypicalOpenAddressBook;
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

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ShowCommand}.
 */
public class ShowCommandTest {

    private Model model = new ModelManager(getTypicalOpenAddressBook(), getTypicalFeedList(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Eatery eateryToShow = model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased());
        ShowCommand showCommand = new ShowCommand(INDEX_FIRST_EATERY);
        CommandResult commandResult = new CommandResult(
                String.format(ShowCommand.MESSAGE_SHOW_EATERY_SUCCESS, eateryToShow.getName()), eateryToShow);

        assertCommandSuccess(showCommand, model, commandResult, model);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEateryList().size() + 1);
        ShowCommand showCommand = new ShowCommand(outOfBoundIndex);

        assertCommandFailure(showCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ShowCommand showFirstCommand = new ShowCommand(INDEX_FIRST_EATERY);
        ShowCommand showSecondCommand = new ShowCommand(INDEX_SECOND_EATERY);

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        ShowCommand showFirstCommandCopy = new ShowCommand(INDEX_FIRST_EATERY);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different eatery -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }
}
