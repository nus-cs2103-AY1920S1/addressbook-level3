package seedu.address.storage;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.classid.ClassId;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Picture;
import seedu.address.model.person.Result;
import seedu.address.model.task.Marking;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskTime;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String description;
    private final String marking;
    private final List<JsonAdaptedTaskTime> taskTimes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("description") String description, @JsonProperty("marking") String marking,
                             @JsonProperty("taskTimes") List<JsonAdaptedTaskTime> taskTimes) {
        this.description = description;
        this.marking = marking;
        this.taskTimes.addAll(taskTimes);
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        description = source.getDescription().fullTaskDescription;
        marking = source.getMarking().status;
        taskTimes.addAll(source.getTime().stream().map(JsonAdaptedTaskTime::new).collect(Collectors.toList()));
    }
    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        final List<TaskTime> times = new ArrayList<>();
        for (JsonAdaptedTaskTime time : taskTimes) {
            times.add(time.toModelType());
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskDescription.class.getSimpleName()));
        }

        final TaskDescription modelDescription = new TaskDescription(description);

        if (marking == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Marking.class.getSimpleName()));
        }

        if (!Marking.isValidMark(marking)) {
            throw new IllegalValueException(Marking.MESSAGE_CONSTRAINTS);
        }
        final Marking modelMarking = new Marking(marking);

        final Set<TaskTime> modelTimes = new HashSet<>(times);

        return new Task(modelDescription, modelTimes, modelMarking);
    }

}
