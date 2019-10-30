package seedu.mark.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.reminder.Note;
import seedu.mark.model.reminder.Reminder;

/**
 * JSON friendly version of {@link Reminder}.
 */
public class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";
    private static final String DATE_FORMATTER = "dd/MM/yyyy HHmm";
    protected static final String MESSAGE_INVALID_TIME_FORMAT =
            "Reminder time has the wrong format! Please use the following format: " + DATE_FORMATTER;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

    private final String note;
    private final String time;
    private final String url;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given reminder details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("note") String note,
                               @JsonProperty("time") String time, @JsonProperty("url") String url) {
        this.note = note;
        this.time = time;
        this.url = url;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        note = source.getNote().toString();
        time = source.getFormattedTime();
        url = source.getUrl().toString();
    }

    /**
     * Converts this Jackson-friendly adapted reminder object into the model's {@code Reminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder.
     */
    public Reminder toModelType() throws IllegalValueException {

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName()));
        }
        if (!Note.isValidNote(note)) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }
        final Note modelNote = new Note(note);

        if (url == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Url.class.getSimpleName()));
        }
        if (!Url.isValidUrl(url)) {
            throw new IllegalValueException(Url.MESSAGE_CONSTRAINTS);
        }
        final Url modelUrl = new Url(url);

        if (time == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName()));
        }

        final LocalDateTime modelTime = getModelTime(time);

        return new Reminder(modelUrl, modelTime, modelNote);
    }

    /**
     * Parses a {@code String time} into a {@code LocalDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code time} is invalid.
     */
    public static LocalDateTime getModelTime(String time) throws IllegalValueException {
        LocalDateTime getTime;

        try {
            getTime = LocalDateTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(MESSAGE_INVALID_TIME_FORMAT);
        }

        return getTime;
    }
}

