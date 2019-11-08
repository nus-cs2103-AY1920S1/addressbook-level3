package seedu.address.storage.calendar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskDeadline;
import seedu.address.model.calendar.task.TaskDescription;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String title;
    private final String day;
    private final String description;
    private final String deadline;
    private final String time;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final int week;
    private final boolean isPersistent;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("title") String title, @JsonProperty("day") String day,
                           @JsonProperty("description") String description, @JsonProperty("time") String time,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                           @JsonProperty("deadline") String deadline, @JsonProperty("week") int week,
                           @JsonProperty("isPersistent") boolean isPersistent) {
        this.title = title;
        this.day = day;
        this.description = description;
        this.time = time;
        this.deadline = deadline;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.week = week;
        this.isPersistent = isPersistent;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        title = source.getTaskTitle().fullName;
        day = source.getTaskDay().value;
        description = source.getTaskDescription().value;
        deadline = source.getTaskDeadline().getValue();
        time = source.getTaskTime().value;
        tagged.addAll(source.getTaskTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        week = source.getWeek();
        isPersistent = source.isPersistent();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the calendarModel's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        final List<TaskTag> personTaskTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTaskTags.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskTitle.class.getSimpleName()));
        }
        if (!TaskTitle.isValidTitle(title)) {
            throw new IllegalValueException(TaskTitle.MESSAGE_CONSTRAINTS);
        }
        final TaskTitle modelTaskTitle = new TaskTitle(title);

        if (day == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDay.class.getSimpleName()));
        }
        if (!TaskDay.isValidDay(day)) {
            throw new IllegalValueException(TaskDay.MESSAGE_CONSTRAINTS);
        }
        final TaskDay modelTaskDay = new TaskDay(day);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDescription.class.getSimpleName()));
        }
        if (!TaskDescription.isValidDescription(description)) {
            throw new IllegalValueException(TaskDescription.MESSAGE_CONSTRAINTS);
        }
        final TaskDescription modelTaskDescription = new TaskDescription(description);

        if (deadline == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDeadline.class.getSimpleName()));
        }
        if (!TaskDeadline.isValidDeadline(deadline)) {
            throw new IllegalValueException(TaskDeadline.MESSAGE_CONSTRAINTS);
        }
        final TaskDeadline modelTaskDeadline = new TaskDeadline(deadline);

        if (time == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskTime.class.getSimpleName()));
        }
        if (!TaskTime.isValidTime(time)) {
            throw new IllegalValueException(TaskTime.MESSAGE_CONSTRAINTS);
        }
        final TaskTime modelTaskTime = new TaskTime(time);

        final Set<TaskTag> modelTaskTags = new HashSet<>(personTaskTags);
        return new Task(modelTaskTitle, modelTaskDay, modelTaskDescription, modelTaskDeadline,
            modelTaskTime, modelTaskTags, week, isPersistent) {
        };
    }

}
