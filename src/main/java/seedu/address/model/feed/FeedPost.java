package seedu.address.model.feed;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a post in the feed list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class FeedPost {
    private final String source;
    private final String title;
    private final String address;

    /**
     * Every field must be present and not null.
     */
    public FeedPost(String source, String title, String address) {
        requireAllNonNull(source, title, address);
        this.source = source;
        this.title = title;
        this.address = address;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }


    public String getAddress() {
        return address;
    }

    /**
     * Returns true if both posts have the same source, title and address.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FeedPost)) {
            return false;
        }

        FeedPost otherFeedPost = (FeedPost) other;
        return otherFeedPost.getTitle().equals(getTitle())
                && otherFeedPost.getSource().equals(getSource())
                && otherFeedPost.getAddress().equals(getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, title, address);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s", getSource(), getTitle(), getAddress());
    }

}
