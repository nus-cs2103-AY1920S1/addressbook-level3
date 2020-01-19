package seedu.mark.testutil;

import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_AMY;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import seedu.mark.model.bookmark.Url;
import seedu.mark.model.reminder.Note;
import seedu.mark.model.reminder.Reminder;


/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {
    public static final String DEFAULT_NOTE = Note.DEFAULT_VALUE;
    public static final String DEFAULT_URL = VALID_URL_AMY;
    public static final String DEFAULT_TIME = "12/12/2020 1800";
    private static final String DATE_FORMATTER = "dd/MM/uuuu HHmm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMATTER)
                                                                        .withResolverStyle(ResolverStyle.STRICT);


    private Note note;
    private Url url;
    private LocalDateTime time;

    public ReminderBuilder() {
        note = new Note(DEFAULT_NOTE);
        url = new Url(DEFAULT_URL);
        time = LocalDateTime.parse(DEFAULT_TIME, FORMATTER);
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        note = reminderToCopy.getNote();
        url = reminderToCopy.getUrl();
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
     * Sets the {@code Url} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withUrl(String url) {
        this.url = new Url(url);
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
        return new Reminder(url, time, note);
    }

}
