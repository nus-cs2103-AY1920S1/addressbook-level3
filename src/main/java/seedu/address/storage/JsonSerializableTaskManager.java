package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskManager;

/**
 * A Task Manager that is serializable to JSON format.
 */
@JsonRootName(value = "taskmanager")
class JsonSerializableTaskManager {

    public static final String MESSAGE_DUPLICATE_TASK = "Task list contains duplicate task(s).";

    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTaskManager} with the given persons.
     */
    @JsonCreator
    public JsonSerializableTaskManager(@JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code TaskManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableTaskManager(TaskManager source) {
        tasks.addAll(source.getList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this task manager into the model's {@code TaskManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TaskManager toModelType() throws IllegalValueException {
        TaskManager taskManager = new TaskManager();
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            if (taskManager.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            taskManager.addTask(task);
        }
        return taskManager;
    }

}
