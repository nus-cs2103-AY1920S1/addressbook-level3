package seedu.mark.model.bookmark;

import java.util.HashMap;
import java.util.Objects;

import seedu.mark.model.annotation.Annotation;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.ParagraphIdentifier;

/**
 * Represents a cached copy of the contents of a Bookmark.
 */
public class CachedCopy {

    /**
     * The raw HTML string of the website.
     */
    public final String html;

    public final OfflineDocument annotations;

    /**
     * Instantiates a new CachedCopy.
     *
     * @param html the html of the website
     */
    public CachedCopy(String html) {
        this.html = html;
        this.annotations = new OfflineDocument(html);
    }

    /**
     * Instantiates an existing CachedCopy that has annotations.
     * @param html the html of the website
     * @param annotations the saved annotations
     */
    public CachedCopy(String html, HashMap<Annotation, ParagraphIdentifier> annotations) {
        this(html);
        this.annotations.loadAnnotations(annotations);
    }

    public OfflineDocument getAnnotations() {
        return annotations;
    }

    @Override
    public int hashCode() {
        return Objects.hash(html);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CachedCopy)) {
            return false;
        }

        CachedCopy otherCachedCopy = (CachedCopy) other;

        return otherCachedCopy.html.equals(html);
    }

    @Override
    public String toString() {
        return html;
    }
}
