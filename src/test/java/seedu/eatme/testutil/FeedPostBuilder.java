package seedu.eatme.testutil;

import seedu.eatme.model.feed.FeedPost;

/**
 * A utility class to help with building FeedPost objects.
 */
public class FeedPostBuilder {

    public static final String DEFAULT_SOURCE = "Default Source";
    public static final String DEFAULT_TITLE = "Sample Post";
    public static final String DEFAULT_ADDRESS = "https://example.com/post";

    private String source;
    private String title;
    private String address;

    public FeedPostBuilder() {
        source = DEFAULT_SOURCE;
        title = DEFAULT_TITLE;
        address = DEFAULT_ADDRESS;
    }

    /**
     * Initializes the FeedPostBuilder with the data of {@code feedPostToCopy}.
     */
    public FeedPostBuilder(FeedPost feedPostToCopy) {
        source = feedPostToCopy.getSource();
        title = feedPostToCopy.getTitle();
        address = feedPostToCopy.getAddress();
    }

    /**
     * Sets the {@code source} of the {@code FeedPost} that we are building.
     */
    public FeedPostBuilder withSource(String source) {
        this.source = source;
        return this;
    }

    /**
     * Sets the {@code title} of the {@code FeedPost} that we are building.
     */
    public FeedPostBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the {@code address} of the {@code FeedPost} that we are building.
     */
    public FeedPostBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public FeedPost build() {
        return new FeedPost(source, title, address);
    }

}
