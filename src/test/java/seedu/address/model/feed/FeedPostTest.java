package seedu.address.model.feed;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FeedPostBuilder;

public class FeedPostTest {

    @Test
    public void equals() {
        // same values -> returns true
        FeedPost feedPost = new FeedPostBuilder().build();
        FeedPost feedPostCopy = new FeedPostBuilder(feedPost).build();
        assertTrue(feedPost.equals(feedPostCopy));

        // same object -> returns true
        assertTrue(feedPost.equals(feedPost));

        // null -> returns false
        assertFalse(feedPost.equals(null));

        // different type -> returns false
        assertFalse(feedPost.equals(5));

        // different feed post -> returns false
        FeedPost differentFeedPost = new FeedPostBuilder()
                .withSource("Different source")
                .withTitle("Different title")
                .withAddress("https://different.com/post")
                .build();
        assertFalse(feedPost.equals(differentFeedPost));

        // different source -> returns false
        FeedPost differentSourceFeedPost = new FeedPostBuilder(feedPost).withSource("Different source").build();
        assertFalse(feedPost.equals(differentSourceFeedPost));

        // different title -> returns false
        FeedPost differentTitleFeedPost = new FeedPostBuilder(feedPost).withTitle("Different title").build();
        assertFalse(feedPost.equals(differentTitleFeedPost));

        // different address -> returns false
        FeedPost differentAddressFeedPost = new FeedPostBuilder(feedPost).withAddress("https://different.com/post")
                .build();
        assertFalse(feedPost.equals(differentAddressFeedPost));
    }
}
