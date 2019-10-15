package seedu.mark.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.model.reminder.Note;

/**
 * JSON friendly version of {@link Reminder}.
 */
public class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    private final String note;
    private final String time;
    private final String bookmark;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given reminder details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("note") String note,
                               @JsonProperty("time") String time, @JsonProperty("bookmark") String bookmark) {
        this.note = note;
        this.time = time;
        this.bookmark = bookmark;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        note = source.getNote().toString();
        time = source.getFormattedTime();
        bookmark = source.getBookmark().getName().value;
    }

}
