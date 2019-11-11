package seedu.address.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.classid.ClassId;
import seedu.address.model.task.Marking;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskTime;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String classId;
    private final String marking;
    private final List<JsonAdaptedTaskTime> taskTimes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("classId") String classId, @JsonProperty("marking") String marking,
                             @JsonProperty("taskTimes") List<JsonAdaptedTaskTime> taskTimes) {
        this.classId = classId;
        this.marking = marking;
        if (taskTimes != null) {
            this.taskTimes.addAll(taskTimes);
        }
        Collections.sort(taskTimes);
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        classId = source.getClassId().value;
        marking = source.getMarking().getStatus();
        taskTimes.addAll(source.getTime().stream().map(JsonAdaptedTaskTime::new).collect(Collectors.toList()));
    }
    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        final TreeSet<TaskTime> times = new TreeSet<>(TaskTime::compareTo);
        for (JsonAdaptedTaskTime time : taskTimes) {
            times.add(time.toModelType());
        }

        if (classId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClassId.class.getSimpleName()));
        }

        final ClassId id = new ClassId(classId);

        if (marking == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Marking.class.getSimpleName()));
        }

        if (!Marking.isValidMark(marking)) {
            throw new IllegalValueException(Marking.MESSAGE_CONSTRAINTS);
        }
        final Marking modelMarking = new Marking(marking);

        final TreeSet<TaskTime> modelTimes = new TreeSet<>(times);

        return new Task(id, modelTimes, modelMarking);
    }

}
