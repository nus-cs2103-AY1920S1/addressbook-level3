package seedu.address.testutil;

import seedu.address.model.FeedList;
import seedu.address.model.feed.Feed;

/**
 * A utility class containing a list of {@code Feed} objects to be used in tests.
 */
public class TypicalFeeds {
    public static FeedList getTypicalFeedList() {
        FeedList feedList = new FeedList();
        feedList.addFeed(new Feed("Seth Lui", "https://sethlui.com/feed"));
        feedList.addFeed(new Feed("Eatbook", "https://eatbook.sg/feed"));
        return feedList;
    }
}
