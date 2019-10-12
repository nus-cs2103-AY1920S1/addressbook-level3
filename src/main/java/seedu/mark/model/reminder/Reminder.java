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

    //TODO: temporarily; to change when confirmed
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

    /**
     * Returns true if both reminders have the same note, the same bookmark and the same time.
     */
    public boolean isSameReminder(Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }

        return otherReminder != null
                && otherReminder.getNote().equals(getNote())
                && otherReminder.getFormattedTime().equals(getFormattedTime())
                && otherReminder.getBookmark().equals(getBookmark());
    }

    /**
     * Returns true if both reminders have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder.getNote().equals(getNote())
                && otherReminder.getFormattedTime().equals(getFormattedTime())
                && otherReminder.getBookmark().equals(getBookmark());
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
                .append(" Bookmark: ")
                .append(getBookmark().getName())
                .append(" Time: ")
                .append(getFormattedTime());
        return builder.toString();
    }

    /**
     * Turn reminding time to the formatted string.
     *
     * @return formatted time string.
     */
    public String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String formatTime = remindTime.format(formatter);
        return formatTime;
    }
}
