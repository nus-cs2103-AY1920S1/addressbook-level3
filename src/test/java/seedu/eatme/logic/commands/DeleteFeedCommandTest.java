package seedu.eatme.logic.commands;

import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;

import org.junit.jupiter.api.Test;

import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;
import seedu.eatme.model.feed.Feed;

/**
 * Contains integration tests (interaction with the Model) for {@code DeleteFeedCommand}.
 */
public class DeleteFeedCommandTest {

    private Model model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());

    @Test
    public void execute_validName_success() {
        Feed feedToDelete = model.getFeedList().getFeedList().get(0);
        DeleteFeedCommand deleteCommand = new DeleteFeedCommand(feedToDelete.getName());

        String expectedMessage = String.format(DeleteFeedCommand.MESSAGE_DELETE_FEED_SUCCESS,
                feedToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
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
