package seedu.jarvis.model.planner.tasks;

import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.enums.Status;
import seedu.jarvis.model.planner.enums.TaskType;
import seedu.jarvis.storage.planner.JsonAdaptedTask;
import seedu.jarvis.storage.planner.JsonAdaptedTodo;

/**
 * Represents a {@code Todo} task in JARVIS
 */
public class Todo extends Task {

    public Todo(String taskDes, Priority priority, Frequency frequency, Status status, Set<Tag> tags) {
        super(taskDes, priority, frequency, status, tags);
    }

    public Todo(String taskDes) {
        this(taskDes, null, null, Status.NOT_DONE, new HashSet<>());
    }

    /**
     * Gets the {@code JsonAdaptedTask} for this task.
     *
     * @return {@code JsonAdaptedTask}.
     */
    @Override
    public JsonAdaptedTask adaptToJsonAdaptedTask() {
        return new JsonAdaptedTodo(this);
    }

    @Override
    public String toString() {
        return "Todo: " + this.taskDes + attributesString();
    }

    /**
     * Retrieves the {@code TaskType} of the task
     * @return {@TaskType} of the task
     */
    @Override
    public TaskType getTaskType() {
        return TaskType.TODO;
    }

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task, same description & same status
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Todo)) {
            return false;
        }

        return (taskDes.equals(((Todo) other).taskDes)) && status.equals(((Todo) other).getStatus());
    }
}
