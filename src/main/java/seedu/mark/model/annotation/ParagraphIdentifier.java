package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.mark.commons.core.index.Index;

/**
 * Represents the identifier for a paragraph. Each identifier includes a {@code type} and an {@code index}.
 */
public class ParagraphIdentifier {
    private Index index;
    private ParagraphType type;

    public ParagraphIdentifier(Index idx, ParagraphType type) {
        requireNonNull(idx);
        requireNonNull(type);

        this.index = idx;
        this.type = type;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ParagraphIdentifier
                && ((ParagraphIdentifier) other).index == this.index
                && ((ParagraphIdentifier) other).type == this.type);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(index, type);
    }

    @Override
    public String toString() {
        return String.format("%s%d", type, index.getOneBased());
    }

    /**
     * Type of paragraph. Paragraphs that {@code EXIST} have content; those that are {@code STRAY} are previously
     * detached from their original paragraphs and currently attached to phantom paragraphs (no content).
     */
    public static enum ParagraphType {
        EXIST, STRAY;
    }
}
