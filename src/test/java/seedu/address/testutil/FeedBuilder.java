package seedu.address.testutil;

import seedu.address.model.feed.Feed;

/**
 * A utility class to help with building Feed objects.
 */
public class FeedBuilder {

    public static final String DEFAULT_NAME = "Example Feed";
    public static final String DEFAULT_ADDRESS = "https://example.com/feed";

    private String name;
    private String address;

    public FeedBuilder() {
        name = DEFAULT_NAME;
        address = DEFAULT_ADDRESS;
    }

    /**
     * Initializes the FeedBuilder with the data of {@code feedToCopy}.
     */
    public FeedBuilder(Feed feedToCopy) {
        name = feedToCopy.getName();
        address = feedToCopy.getAddress();
    }

    /**
     * Sets the {@code name} of the {@code Feed} that we are building.
     */
    public FeedBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code address} of the {@code Feed} that we are building.
     */
    public FeedBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public Feed build() {
        return new Feed(name, address);
    }

}
