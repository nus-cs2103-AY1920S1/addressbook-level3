package seedu.address.model.feed;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FeedBuilder;

public class FeedTest {

    @Test
    public void equals() {
        // same values -> returns true
        Feed feed = new FeedBuilder().build();
        Feed feedCopy = new FeedBuilder(feed).build();
        assertTrue(feed.equals(feedCopy));

        // same object -> returns true
        assertTrue(feed.equals(feed));

        // null -> returns false
        assertFalse(feed.equals(null));

        // different type -> returns false
        assertFalse(feed.equals(5));

        // different feed -> returns false
        Feed differentFeed = new FeedBuilder()
                .withName("Different Feed").withAddress("https://different.com/feed").build();
        assertFalse(feed.equals(differentFeed));

        // different name -> returns false
        Feed differentNameFeed = new FeedBuilder(feed).withName("Different Name").build();
        assertFalse(feed.equals(differentNameFeed));

        // different address -> returns false
        Feed differentAddressFeed = new FeedBuilder(feed).withAddress("https://different.com/feed").build();
        assertFalse(feed.equals(differentAddressFeed));
    }
}
