package seedu.mark.model.reminder;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.mark.model.bookmark.Bookmark;


/**
 * Represent a reminder in Mark.
 */
public class Reminder {

    //temporarily
    private static final String DATE_FORMATTER = "dd/MM/yyyy HHmm";

    //data field
    private Bookmark bookmark;
    private LocalDateTime remindTime;

    //identity field
    private Note note;

    /**
     * Every field must be present and not null.
     *
     * @param bookmark the bookmark the reminder should open.
     * @param time the reminding time.
     * @param note the note for reminder.
     */
    public Reminder(Bookmark bookmark, LocalDateTime time, Note note) {
        requireAllNonNull(bookmark, time, note);
        this.bookmark = bookmark;
        this.remindTime = time;
        this.note = note;
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public LocalDateTime getRemindTime() {
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
                .append(timeFormatter());
        return builder.toString();
    }

    /**
     * Turn reminding time to the formatted string.
     *
     * @return formatted time string.
     */
    public String timeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String formatTime = remindTime.format(formatter);
        return formatTime;
    }
}
