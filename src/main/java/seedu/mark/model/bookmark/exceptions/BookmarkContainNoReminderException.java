package seedu.mark.model.bookmark.exceptions;

/**
 * Signals that the operation is unable to find the reminder in the specified bookmark.
 */
public class BookmarkContainNoReminderException extends RuntimeException{
    public BookmarkContainNoReminderException() {
        super("This bookmark has no reminder.");
    }
}
