package seedu.address.model.feed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.feed.exceptions.DuplicateFeedException;
import seedu.address.model.feed.exceptions.FeedNotFoundException;
import seedu.address.testutil.FeedBuilder;

public class UniqueFeedListTest {

    private final UniqueFeedList uniqueFeedList = new UniqueFeedList();
    private Feed feed = new FeedBuilder().build();

    @Test
    public void contains_nullFeed_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFeedList.contains(null));
    }

    @Test
    public void contains_feedNotInList_returnsFalse() {
        assertFalse(uniqueFeedList.contains(feed));
    }

    @Test
    public void contains_feedInList_returnsTrue() {
        uniqueFeedList.add(feed);
        assertTrue(uniqueFeedList.contains(feed));
    }

    @Test
    public void add_nullFeed_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFeedList.add(null));
    }

    @Test
    public void add_duplicateFeed_throwsDuplicateFeedException() {
        uniqueFeedList.add(feed);
        assertThrows(DuplicateFeedException.class, () -> uniqueFeedList.add(feed));
    }

    @Test
    public void setFeed_nullTargetFeed_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFeedList.setFeed(null, feed));
    }

    @Test
    public void setFeed_nullEditedFeed_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFeedList.setFeed(feed, null));
    }

    @Test
    public void setFeed_targetFeedNotInList_throwsFeedNotFoundException() {
        assertThrows(FeedNotFoundException.class, () -> uniqueFeedList.setFeed(feed, feed));
    }

    @Test
    public void setFeed_editedFeedIsSameFeed_success() {
        uniqueFeedList.add(feed);
        uniqueFeedList.setFeed(feed, feed);
        UniqueFeedList expectedUniqueFeedList = new UniqueFeedList();
        expectedUniqueFeedList.add(feed);
        assertEquals(expectedUniqueFeedList, uniqueFeedList);
    }

    @Test
    public void remove_nullFeed_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFeedList.remove(null));
    }

    @Test
    public void remove_feedDoesNotExist_throwsFeedNotFoundException() {
        assertThrows(FeedNotFoundException.class, () -> uniqueFeedList.remove(feed));
    }

    @Test
    public void remove_existingFeed_removesFeed() {
        uniqueFeedList.add(feed);
        uniqueFeedList.remove(feed);
        UniqueFeedList expectedUniqueFeedList = new UniqueFeedList();
        assertEquals(expectedUniqueFeedList, uniqueFeedList);
    }

    @Test
    public void setFeeds_nullUniqueFeedList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFeedList.setFeeds((UniqueFeedList) null));
    }

    @Test
    public void setFeeds_uniqueFeedList_replacesOwnListWithProvidedUniqueFeedList() {
        uniqueFeedList.add(feed);
        UniqueFeedList expectedUniqueFeedList = new UniqueFeedList();
        expectedUniqueFeedList.add(new FeedBuilder(feed).withName("Different feed").build());
        uniqueFeedList.setFeeds(expectedUniqueFeedList);
        assertEquals(expectedUniqueFeedList, uniqueFeedList);
    }

    @Test
    public void setFeeds_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFeedList.setFeeds((List<Feed>) null));
    }

    @Test
    public void setFeeds_list_replacesOwnListWithProvidedList() {
        Feed differentFeed = new FeedBuilder().withName("Different name").build();
        uniqueFeedList.add(feed);
        List<Feed> feedList = Collections.singletonList(differentFeed);
        uniqueFeedList.setFeeds(feedList);
        UniqueFeedList expectedUniqueFeedList = new UniqueFeedList();
        expectedUniqueFeedList.add(differentFeed);
        assertEquals(expectedUniqueFeedList, uniqueFeedList);
    }

    @Test
    public void setFeeds_listWithDuplicateFeeds_throwsDuplicateFeedException() {
        List<Feed> listWithDuplicateFeeds = Arrays.asList(feed, feed);
        assertThrows(DuplicateFeedException.class, () -> uniqueFeedList.setFeeds(listWithDuplicateFeeds));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
            UnsupportedOperationException.class, (
            ) -> uniqueFeedList.asUnmodifiableObservableList().remove(0)
        );
    }
}
