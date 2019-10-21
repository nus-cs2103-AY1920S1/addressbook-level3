package seedu.address.storage.calendar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendar.person.Task;
import seedu.address.model.calendar.person.TaskDay;
import seedu.address.model.calendar.person.TaskDeadline;
import seedu.address.model.calendar.person.TaskDescription;
import seedu.address.model.calendar.person.TaskPlace;
import seedu.address.model.calendar.person.TaskTitle;
import seedu.address.model.calendar.tag.TaskTag;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String title;
    private final String time;
    private final String description;
    private final String deadline;
    private final String place;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String title, @JsonProperty("phone") String time,
                             @JsonProperty("email") String description, @JsonProperty("address") String place,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                             @JsonProperty("deadline") String deadline) {
        this.title = title;
        this.time = time;
        this.description = description;
        this.place = place;
        this.deadline = deadline;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Task source) {
        title = source.getTaskTitle().fullName;
        time = source.getTaskDay().value;
        description = source.getTaskDescription().value;
        deadline = source.getTaskDeadline().getValue();
        place = source.getTaskPlace().value;
        tagged.addAll(source.getTaskTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
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
        if (!TaskTitle.isValidName(title)) {
            throw new IllegalValueException(TaskTitle.MESSAGE_CONSTRAINTS);
        }
        final TaskTitle modelTaskTitle = new TaskTitle(title);

        if (time == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDay.class.getSimpleName()));
        }
        if (!TaskDay.isValidDay(time)) {
            throw new IllegalValueException(TaskDay.MESSAGE_CONSTRAINTS);
        }
        final TaskDay modelTaskDay = new TaskDay(time);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDescription.class.getSimpleName()));
        }
        if (!TaskDescription.isValidEmail(description)) {
            throw new IllegalValueException(TaskDescription.MESSAGE_CONSTRAINTS);
        }
        final TaskDescription modelTaskDescription = new TaskDescription(description);

        if (deadline == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDeadline.class.getSimpleName()));
        }
        if (!TaskDeadline.isValidDate(deadline)) {
            throw new IllegalValueException(TaskDeadline.MESSAGE_CONSTRAINTS);
        }
        final TaskDeadline modelTaskDeadline = new TaskDeadline(deadline);

        if (place == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskPlace.class.getSimpleName()));
        }
        if (!TaskPlace.isValidAddress(place)) {
            throw new IllegalValueException(TaskPlace.MESSAGE_CONSTRAINTS);
        }
        final TaskPlace modelTaskPlace = new TaskPlace(place);

        final Set<TaskTag> modelTaskTags = new HashSet<>(personTaskTags);
        return new Task(modelTaskTitle, modelTaskDay, modelTaskDescription, modelTaskDeadline,
                modelTaskPlace, modelTaskTags);
    }

}
