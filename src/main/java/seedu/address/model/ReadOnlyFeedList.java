package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.feed.Feed;

/**
 * Unmodifiable view of the feed list.
 */
public interface ReadOnlyFeedList {

    /**
     * Returns an unmodifiable view of the feed list.
     * This list will not contain any duplicate feeds.
     */
    ObservableList<Feed> getFeedList();

}
