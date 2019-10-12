package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ProjectDashboard;
import seedu.address.model.ReadOnlyProjectDashboard;
import seedu.address.model.task.Task;

/**
 * An Immutable ProjectDashboard that is serializable to JSON format.
 */
// TODO change serializable object root name
@JsonRootName(value = "projectdashboard")
class JsonSerializableProjectDashboard {

    public static final String MESSAGE_DUPLICATE_TASKS = "Tasks list contains duplicate task(s).";

    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableProjectDashboard} with the given task.
     */
    @JsonCreator
    public JsonSerializableProjectDashboard(@JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code ReadOnlyProjectDashboard} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableProjectDashboard}.
     */
    public JsonSerializableProjectDashboard(ReadOnlyProjectDashboard source) {
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this project information into the model's {@code ProjectDashboard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ProjectDashboard toModelType() throws IllegalValueException {
        ProjectDashboard projectDashboard = new ProjectDashboard();
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            if (projectDashboard.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASKS);
            }
            projectDashboard.addTask(task);
        }
        return projectDashboard;
    }

}
