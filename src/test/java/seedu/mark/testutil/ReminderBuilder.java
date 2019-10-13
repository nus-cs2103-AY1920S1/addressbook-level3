package seedu.mark.testutil;

import static seedu.mark.testutil.TypicalBookmarks.ALICE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.reminder.Note;
import seedu.mark.model.reminder.Reminder;


/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {
    public static final String DEFAULT_NOTE = Note.DEFAULT_VALUE;
    public static final Bookmark DEFAULT_BOOKMARK = ALICE;
    public static final String DEFAULT_TIME = "12/12/2020 1800";
    private static final String DATE_FORMATTER = "dd/MM/yyyy HHmm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMATTER);


    private Note note;
    private Bookmark bookmark;
    private LocalDateTime time;

    public ReminderBuilder() {
        note = new Note(DEFAULT_NOTE);
        bookmark = (new BookmarkBuilder(DEFAULT_BOOKMARK)).build();
        time = LocalDateTime.parse(DEFAULT_TIME, FORMATTER);
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        note = reminderToCopy.getNote();
        bookmark = reminderToCopy.getBookmark();
        time = reminderToCopy.getRemindTime();
    }

    /**
     * Sets the {@code Note} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code Bookmark} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withBookmark(Bookmark bookmark) {
        this.bookmark = (new BookmarkBuilder(bookmark)).build();
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withTime(String time) {
        this.time = LocalDateTime.parse(time, FORMATTER);
        return this;
    }

    public Reminder build() {
        return new Reminder(bookmark, time, note);
    }

}
