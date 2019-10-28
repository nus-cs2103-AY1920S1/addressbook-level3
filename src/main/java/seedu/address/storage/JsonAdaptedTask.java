package seedu.address.storage;

import static seedu.address.model.task.Task.FORMAT_FILE_DATE_STRING;
import static seedu.address.model.task.Task.FORMAT_FILE_TIME_STRING;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Heading;
import seedu.address.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Revision task's %s field is missing!";

    private final String heading;
    private final String status;
    private final String date;
    private final String time;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given revision task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("heading") String heading, @JsonProperty("isDone") String status,
                           @JsonProperty("date") LocalDate date, @JsonProperty("time") LocalTime time) {
        this.heading = heading;
        this.status = status;
        this.date = date.format(FORMAT_FILE_DATE_STRING);
        this.time = time.format(FORMAT_FILE_TIME_STRING);
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        heading = source.getHeading().toString();
        status = source.getStatusIcon();
        date = source.getDate().toString();
        time = source.getTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted note.
     */
    public Task toModelType() throws IllegalValueException {
        if (heading == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Heading.class.getSimpleName()));
        }

        final Heading modelHeading = new Heading(heading);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Status icon"));
        }

        if (!Task.isValidStatusIcon(status)) {
            throw new IllegalValueException("Status icon can either [Y] or [N] only");
        }

        final boolean isDone = status.equals("[Y]");

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }

        final LocalDate modelDate = LocalDate.parse(date, FORMAT_FILE_DATE_STRING);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "time"));
        }

        final LocalTime modelTime = LocalTime.parse(time, FORMAT_FILE_TIME_STRING);

        return new Task(modelHeading, modelDate, modelTime, isDone);
    }
}
