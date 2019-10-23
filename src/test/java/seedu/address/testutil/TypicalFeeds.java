package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_EATBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_SETHLUI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EATBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SETHLUI;

import seedu.address.model.FeedList;
import seedu.address.model.feed.Feed;

/**
 * A utility class containing a list of {@code Feed} objects to be used in tests.
 */
public class TypicalFeeds {
    public static final Feed EATBOOK = new FeedBuilder()
            .withName(VALID_NAME_EATBOOK).withAddress(VALID_ADDRESS_EATBOOK).build();
    public static final Feed SETHLUI = new FeedBuilder()
            .withName(VALID_NAME_SETHLUI).withAddress(VALID_ADDRESS_SETHLUI).build();

    public static FeedList getTypicalFeedList() {
        FeedList feedList = new FeedList();
        feedList.addFeed(EATBOOK);
        feedList.addFeed(SETHLUI);
        return feedList;
    }
}
