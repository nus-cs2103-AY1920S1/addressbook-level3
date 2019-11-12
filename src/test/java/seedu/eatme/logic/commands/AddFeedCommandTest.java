package seedu.eatme.logic.commands;

import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;
import seedu.eatme.model.feed.Feed;
import seedu.eatme.testutil.FeedBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddFeedCommand}.
 */
public class AddFeedCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());
    }

    @Test
    public void execute_newFeed_success() {
        Feed validFeed = new FeedBuilder().build();

        Model expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
        expectedModel.addFeed(validFeed);

        assertCommandSuccess(new AddFeedCommand(validFeed), model,
                String.format(AddFeedCommand.MESSAGE_SUCCESS, validFeed.getName()), expectedModel);
    }

    @Test
    public void execute_duplicateFeed_throwsCommandException() {
        Feed feedInFeedList = model.getFeedList().getFeedList().get(0);

        assertCommandFailure(new AddFeedCommand(feedInFeedList), model, AddFeedCommand.MESSAGE_DUPLICATE_FEED);
    }
}
