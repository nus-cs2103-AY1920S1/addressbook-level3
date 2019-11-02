package seedu.address.testutil.tag;

import seedu.address.model.tag.Tag;

/**
 * A utility class to help build names.
 */
public class TagBuilder {
    public static final String TAG_NAME = "Subject";

    private String tagName;

    public TagBuilder() {
        tagName = TAG_NAME;
    }

    /**
     * Initializes the TagBuilder with the data of {@code tagToCopy}.
     */
    public TagBuilder(Tag tagToCopy) {
        tagName = tagToCopy.tagName;
    }

    /**
     * Sets the {@code tagName} of the {@code Tag} that we are building.
     */
    public TagBuilder withName(String name) {
        this.tagName = name;
        return this;
    }

    public Tag build() {
        return new Tag(tagName);
    }
}


