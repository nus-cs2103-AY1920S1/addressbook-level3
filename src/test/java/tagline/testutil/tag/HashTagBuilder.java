package tagline.testutil.tag;

import tagline.model.tag.HashTag;

/**
 * A utility class to help with building Tag objects.
 */
public class HashTagBuilder {

    public static final String DEFAULT_CONTENT = "content";

    private String content;

    public HashTagBuilder() {
        this.content = DEFAULT_CONTENT;
    }

    /**
     * Sets the {@code GroupName} of {@code GroupTag} to be that we are building.
     */
    public HashTagBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public HashTag build() {
        return new HashTag(content);
    }
}
