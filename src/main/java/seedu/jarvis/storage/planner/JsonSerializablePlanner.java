package seedu.jarvis.storage.planner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.storage.JsonAdapter;

/**
 * A {@code Planner} that is serializable to JSON format.
 */
@JsonRootName(value = "planner")
public class JsonSerializablePlanner implements JsonAdapter<Planner> {

    public static final String MESSAGE_DUPLICATE_TASK = "Task list contains duplicate task(s).";

    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePlanner} with the given tasks.
     */
    @JsonCreator
    public JsonSerializablePlanner(@JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code Planner} into this class for Jackson use.
     *
     * @param planner Future changes to this will not affect the created {@code JsonSerializablePlanner}.
     */
    public JsonSerializablePlanner(Planner planner) {
        tasks.addAll(planner.getTasks()
                .stream()
                .map(Task::adaptToJsonAdaptedTask)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this {@code JsonAdaptedPlanner} into the model's {@code Planner} object.
     *
     * @return {@code Planner} of the Jackson-friendly adapted {@code JsonSerializablePlanner}.
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Planner toModelType() throws IllegalValueException {
        Planner planner = new Planner();
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            if (planner.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            planner.addTask(task);
        }
        return planner;
    }
}
