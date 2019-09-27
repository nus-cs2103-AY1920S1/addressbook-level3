package seedu.mark.testutil;

import seedu.mark.model.BookmarkManager;
import seedu.mark.model.bookmark.Bookmark;

/**
 * A utility class to help with building BookmarkManager objects.
 * Example usage: <br>
 *     {@code BookmarkManager ab = new BookmarkManagerBuilder().withBookmark("John", "Doe").build();}
 */
public class BookmarkManagerBuilder {

    private BookmarkManager bookmarkManager;

    public BookmarkManagerBuilder() {
        bookmarkManager = new BookmarkManager();
    }

    public BookmarkManagerBuilder(BookmarkManager bookmarkManager) {
        this.bookmarkManager = bookmarkManager;
    }

    /**
     * Adds a new {@code Bookmark} to the {@code BookmarkManager} that we are building.
     */
    public BookmarkManagerBuilder withBookmark(Bookmark bookmark) {
        bookmarkManager.addBookmark(bookmark);
        return this;
    }

    public BookmarkManager build() {
        return bookmarkManager;
    }
}
