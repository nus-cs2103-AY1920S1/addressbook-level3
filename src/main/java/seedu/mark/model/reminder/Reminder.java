package seedu.mark.model.reminder;

import java.util.Date;
import java.util.Objects;

import seedu.mark.model.bookmark.Bookmark;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represent a reminder in Mark.
 */
public class Reminder {
    //date field
    private Bookmark bookmark;
    private Date remindTime;

    //identity field
    private Note note;

    /**
     * Every field must be present and not null.
     */
    Reminder(Bookmark bookmark, Date time, Note note) {
        requireAllNonNull(bookmark, time, note);
        this.bookmark = bookmark;
        this.remindTime = time;
        this.note = note;
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public Note getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(bookmark, remindTime, note);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getNote())
                .append(" by ")
                .append(getRemindTime());
        return builder.toString();
    }
}
