package seedu.mark.model.reminder;

import seedu.mark.model.bookmark.Bookmark;

import java.util.Date;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represent a reminder in Mark.
 */
public class Reminder {
    private Bookmark bookmark;
    private Date remindTime;
    private Note note;

    /**
     * Every field must be present and not null.
     */
    Reminder(Bookmark bookmark, Date time, Note note){
        requireAllNonNull(bookmark, time, note);
        this.bookmark = bookmark;
        this.remindTime = time;
        this.note = note;
    }
}
