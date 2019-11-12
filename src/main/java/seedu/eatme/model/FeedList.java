package seedu.eatme.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.eatme.model.feed.Feed;
import seedu.eatme.model.feed.UniqueFeedList;

/**
 * Wraps all data at the feed list level
 * Duplicates are not allowed
 */
public class FeedList implements ReadOnlyFeedList {

    private final UniqueFeedList feeds;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        feeds = new UniqueFeedList();
    }

    public FeedList() {
    }

    /**
     * Creates a FeedList using the Feeds in the {@code toBeCopied}
     */
    public FeedList(ReadOnlyFeedList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the feed list with {@code feeds}.
     * {@code feeds} must not contain duplicate feeds.
     */
    public void setFeeds(List<Feed> feeds) {
        this.feeds.setFeeds(feeds);
    }

    /**
     * Resets the existing data of this {@code FeedList} with {@code newData}.
     */
    public void resetData(ReadOnlyFeedList newData) {
        requireNonNull(newData);

        setFeeds(newData.getFeedList());
    }

    //// feed-level operations

    /**
     * Returns true if a feed with the same identity as {@code feed} exists in the feed list.
     */
    public boolean hasFeed(Feed feed) {
        requireNonNull(feed);
        return feeds.contains(feed);
    }

    /**
     * Adds a feed to the feed list.
     * The feed must not already exist in the feed list.
     */
    public void addFeed(Feed p) {
        feeds.add(p);
    }

    /**
     * Replaces the given feed {@code target} in the list with {@code editedFeed}.
     * {@code target} must exist in the feed list.
     * The identity of {@code editedFeed} must not be the same as another existing feed in the feed list.
     */
    public void setFeed(Feed target, Feed editedFeed) {
        requireNonNull(editedFeed);

        feeds.setFeed(target, editedFeed);
    }

    /**
     * Removes {@code key} from this {@code FeedList}.
     * {@code key} must exist in the feed list.
     */
    public void removeFeed(Feed key) {
        feeds.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return feeds.asUnmodifiableObservableList().size() + " feeds";
    }

    @Override
    public ObservableList<Feed> getFeedList() {
        return feeds.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FeedList // instanceof handles nulls
                && feeds.equals(((FeedList) other).feeds));
    }

    @Override
    public int hashCode() {
        return feeds.hashCode();
    }
}
