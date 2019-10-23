package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEateries.getTypicalAddressBook;
import static seedu.address.testutil.TypicalFeeds.getTypicalFeedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.feed.Feed;
import seedu.address.testutil.FeedBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddFeedCommand}.
 */
public class AddFeedCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalFeedList(), new UserPrefs());
    }

    @Test
    public void execute_newFeed_success() {
        Feed validFeed = new FeedBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getFeedList(), new UserPrefs());
        expectedModel.addFeed(validFeed);

        assertCommandSuccess(new AddFeedCommand(validFeed), model,
                String.format(AddFeedCommand.MESSAGE_SUCCESS, validFeed), expectedModel);
    }

    @Test
    public void execute_duplicateFeed_throwsCommandException() {
        Feed feedInFeedList = model.getFeedList().getFeedList().get(0);

        assertCommandFailure(new AddFeedCommand(feedInFeedList), model, AddFeedCommand.MESSAGE_DUPLICATE_FEED);
    }
}
