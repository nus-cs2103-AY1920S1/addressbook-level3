package seedu.mark.testutil;

import seedu.mark.model.Mark;
import seedu.mark.model.bookmark.Bookmark;

/**
 * A utility class to help with building Mark objects.
 * Example usage: <br>
 *     {@code Mark mark = new MarkBuilder().withBookmark("John", "Doe").build();}
 */
public class MarkBuilder {

    private Mark mark;

    public MarkBuilder() {
        mark = new Mark();
    }

    public MarkBuilder(Mark mark) {
        this.mark = mark;
    }

    /**
     * Adds a new {@code Bookmark} to the {@code Mark} that we are building.
     */
    public MarkBuilder withBookmark(Bookmark bookmark) {
        mark.addBookmark(bookmark);
        return this;
    }

    public Mark build() {
        return mark;
    }
}
