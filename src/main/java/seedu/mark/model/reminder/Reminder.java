package seedu.mark.model.reminder;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.mark.model.bookmark.Url;


/**
 * Represents a reminder in Mark.
 */
public class Reminder {

    //TODO: temporarily; to change when confirmed
    private static final String DATE_FORMATTER = "dd/MM/uuuu HHmm";

    //Has the reminder shown
    private boolean isShown = false;
    //Is the reminder Due
    private boolean isDue = false;

    //data fields
    private Url url;
    private LocalDateTime remindTime;

    //identity field
    private Note note;

    /**
     * Every field must be present and not null.
     *
     * @param url the url the reminder should open.
     * @param time the reminding time.
     * @param note the note for reminder.
     */
    public Reminder(Url url, LocalDateTime time, Note note) {
        requireAllNonNull(url, time, note);
        this.url = url;
        this.remindTime = time;
        this.note = note;
    }

    public Url getUrl() {
        return url;
    }

    public LocalDateTime getRemindTime() {
        return remindTime;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Returns true if both reminders have the same note, the same bookmark and the same time.
     * TODO: change implementation based on how to distinguish reminders (which field to use)
     */
    public boolean isSameReminder(Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }

        return otherReminder != null
                && otherReminder.getNote().equals(getNote())
                && otherReminder.getFormattedTime().equals(getFormattedTime())
                && otherReminder.getUrl().equals(getUrl());
    }

    public void toShow() {
        isShown = true;
    }

    public boolean getShow() {
        return isShown;
    }

    public void setDue() {
        isDue = true;
    }

    public boolean getDue() {
        return isDue;
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
                && otherReminder.getUrl().equals(getUrl());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(url, remindTime, note);
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder();
        builder.append(getNote())
                .append(" Time: ")
                .append(getFormattedTime())
                .append(" URL: ")
                .append(getUrl());
        return builder.toString();
    }

    /**
     * Converts reminding time to the formatted string.
     *
     * @return formatted time string.
     */
    public String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String formatTime = remindTime.format(formatter);
        return formatTime;
    }
}
