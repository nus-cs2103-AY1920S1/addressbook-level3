package seedu.eatme.model.feed.exceptions;

/**
 * Signals that the operation will result in duplicate Feeds.
 */
public class DuplicateFeedException extends RuntimeException {
    public DuplicateFeedException() {
        super("Operation would result in duplicate feeds");
    }
}
