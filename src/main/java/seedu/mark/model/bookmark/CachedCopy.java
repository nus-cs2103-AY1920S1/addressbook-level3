package seedu.mark.model.bookmark;

import java.util.Objects;

/**
 * Represents a cached copy of the contents of a Bookmark.
 */
public class CachedCopy {

    /**
     * The raw HTML string of the website.
     */
    public final String html;

    /**
     * Instantiates a new CachedCopy.
     *
     * @param html the html of the website
     */
    public CachedCopy(String html) {
        this.html = html;
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
