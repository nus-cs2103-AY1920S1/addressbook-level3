package seedu.mark.model.bookmark.exceptions;

/**
 * Signals that the operation will result in duplicate Bookmarks (Bookmarks are considered duplicates if they
 * have the same identity).
 */
public class DuplicateBookmarkException extends RuntimeException {
    public DuplicateBookmarkException() {
        super("Operation would result in duplicate bookmarks");
    }
}
