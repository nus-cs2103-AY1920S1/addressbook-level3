package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEateries.getTypicalOpenAddressBook;
import static seedu.address.testutil.TypicalFeeds.getTypicalFeedList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.feed.Feed;

/**
 * Contains integration tests (interaction with the Model) for {@code DeleteFeedCommand}.
 */
public class DeleteFeedCommandTest {

    private Model model = new ModelManager(getTypicalOpenAddressBook(), getTypicalFeedList(), new UserPrefs());

    @Test
    public void execute_validName_success() {
        Feed feedToDelete = model.getFeedList().getFeedList().get(0);
        DeleteFeedCommand deleteCommand = new DeleteFeedCommand(feedToDelete.getName());

        String expectedMessage = String.format(DeleteFeedCommand.MESSAGE_DELETE_FEED_SUCCESS, feedToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getFeedList(), new UserPrefs());
        expectedModel.deleteFeed(feedToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        String invalidName = "Invalid Name";
        DeleteFeedCommand deleteCommand = new DeleteFeedCommand(invalidName);

        assertCommandFailure(deleteCommand, model, DeleteFeedCommand.MESSAGE_MISSING_FEED);
    }
}
