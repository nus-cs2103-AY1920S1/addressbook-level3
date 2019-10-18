package seedu.address.model.feed;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.feed.exceptions.DuplicateFeedException;
import seedu.address.model.feed.exceptions.FeedNotFoundException;

/**
 * A list of feeds that enforces uniqueness between its elements and does not allow nulls.
 * A feed is considered unique by comparing using {@code Feed#equals(Feed)}.
 * Supports a minimal set of list operations.
 *
 * @see Feed#equals(Object)
 */
public class UniqueFeedList implements Iterable<Feed> {
    private final ObservableList<Feed> internalList = FXCollections.observableArrayList();
    private final ObservableList<Feed> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent feed as the given argument.
     */
    public boolean contains(Feed toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a feed to the list.
     * The feed must not already exist in the list.
     */
    public void add(Feed toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFeedException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the feed {@code target} in the list with {@code editedFeed}.
     * {@code target} must exist in the list.
     * The feed identity of {@code editedFeed} must not be the same as another existing feed in the list.
     */
    public void setFeed(Feed target, Feed editedFeed) {
        requireAllNonNull(target, editedFeed);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FeedNotFoundException();
        }

        if (!target.equals(editedFeed) && contains(editedFeed)) {
            throw new DuplicateFeedException();
        }

        internalList.set(index, editedFeed);
    }

    /**
     * Removes the equivalent feed from the list.
     * The feed must exist in the list.
     */
    public void remove(Feed toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FeedNotFoundException();
        }
    }

    public void setFeeds(UniqueFeedList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code feeds}.
     * {@code feeds} must not contain duplicate feeds.
     */
    public void setFeeds(List<Feed> feeds) {
        requireAllNonNull(feeds);
        if (!feedsAreUnique(feeds)) {
            throw new DuplicateFeedException();
        }

        internalList.setAll(feeds);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Feed> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Feed> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFeedList // instanceof handles nulls
                && internalList.equals(((UniqueFeedList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code feeds} contains only unique feeds.
     */
    private boolean feedsAreUnique(List<Feed> feeds) {
        for (int i = 0; i < feeds.size() - 1; i++) {
            for (int j = i + 1; j < feeds.size(); j++) {
                if (feeds.get(i).equals(feeds.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
