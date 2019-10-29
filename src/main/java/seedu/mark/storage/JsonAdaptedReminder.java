package seedu.mark.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.mark.model.reminder.Reminder;

/**
 * JSON friendly version of {@link Reminder}.
 */
public class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

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

}
