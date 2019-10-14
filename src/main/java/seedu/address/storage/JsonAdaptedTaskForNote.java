package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskForNote;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTaskForNote {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Revision task's %s field is missing!";

    private final String title;
    private final String content;
    private final String status;
    private final String date;
    private final String time;

    /**
     * Constructs a {@code JsonAdaptedTaskForNote} with the given revision task details.
     */
    @JsonCreator
    public JsonAdaptedTaskForNote(@JsonProperty("title") String title, @JsonProperty("content") String content,
                                  @JsonProperty("isDone") String status, @JsonProperty("date") LocalDate date,
                                  @JsonProperty("time") LocalTime time) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.date = date.format(Task.FORMAT_FILE_DATE_STRING);
        this.time = time.format(Task.FORMAT_FILE_TIME_STRING);
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTaskForNote(Task source) {
        title = source.getNote().getTitle().title;
        content = source.getNote().getContent().content;
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
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (content == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName()));
        }
        if (!Content.isValidContent(content)) {
            throw new IllegalValueException(Content.MESSAGE_CONSTRAINTS);
        }
        final Content modelContent = new Content(content);

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

        final LocalDate modelDate = LocalDate.parse(date, Task.FORMAT_FILE_DATE_STRING);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "time"));
        }

        final LocalTime modelTime = LocalTime.parse(time, Task.FORMAT_FILE_TIME_STRING);

        final Note modelNote = new Note(modelTitle, modelContent);
        return new TaskForNote(modelNote, modelDate, modelTime, isDone);
    }
}
