package seedu.pluswork.storage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.pluswork.commons.exceptions.IllegalValueException;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String name;
    private final TaskStatus taskStatus;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private String deadline = null;
    private Instant timeStart = null;
    private Instant timeEnd = null;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("name") String name, @JsonProperty("status") TaskStatus taskStatus,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                           @JsonProperty("deadline") String deadline, @JsonProperty("timeStart") Instant timeStart,
                           @JsonProperty("timeEnd") Instant timeEnd) {
        this.name = name;
        this.taskStatus = taskStatus;
        this.deadline = deadline;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        name = source.getName().fullName;
        taskStatus = source.getTaskStatus();
        if (source.hasDeadline()) {
            deadline = source.getDeadline().format(DateTimeFormatter.ISO_DATE_TIME);
        }
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        timeStart = source.getTimeStart();
        timeEnd = source.getTimeEnd();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (taskStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskStatus.class.getSimpleName()));
        }
        if (!Name.isValidMemberName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        final TaskStatus modelTaskStatus = TaskStatus.valueOf(taskStatus.name());
        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Task modelTask = new Task(modelName, modelTaskStatus, modelTags);
        if (deadline != null) {
            final LocalDateTime modelDeadline = LocalDateTime.parse(deadline);
            modelTask.setDeadline(modelDeadline);
        }

        if (timeEnd != null) {
            final Instant modelTimeEnd = timeEnd;
            modelTask.setTimeEnd(modelTimeEnd);
        }

        if (timeStart != null) {
            final Instant modelTimeStart = timeStart;
            modelTask.setTimeStart(timeStart);
        }

        return modelTask;
    }

}
