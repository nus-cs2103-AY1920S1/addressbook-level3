package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

/**
 * Represents the contents of an offline document paragraph.
 * Content supports only {@code String} at status quo.
 */
public class ParagraphContent {

    private String content;

    public ParagraphContent(String content) {
        requireNonNull(content); //but can be blank, as in phantom paragraph

        this.content = content;
    }

    public String getContent() {
        return content;
    }

    /**
     * Returns a copy of this {@code ParagraphContent}.
     */
    public ParagraphContent copy() {
        return new ParagraphContent(content);
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParagraphContent)) {
            return false;
        }
        return this.content.equals(((ParagraphContent) other).content);
    }
}
