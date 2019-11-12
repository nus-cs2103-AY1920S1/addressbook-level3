package seedu.eatme.testutil;

import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_EATBOOK;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_EATBOOK;

import seedu.eatme.model.feed.Feed;

/**
 * A utility class to help with building Feed objects.
 */
public class FeedBuilder {

    private String name;
    private String address;

    public FeedBuilder() {
        name = VALID_NAME_EATBOOK;
        address = VALID_ADDRESS_EATBOOK;
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
