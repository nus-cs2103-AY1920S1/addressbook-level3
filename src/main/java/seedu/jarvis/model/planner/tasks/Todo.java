package seedu.jarvis.model.planner.tasks;

import java.util.Set;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;
import seedu.jarvis.storage.planner.JsonAdaptedTask;
import seedu.jarvis.storage.planner.JsonAdaptedTodo;

/**
 * Represents a Todo task in JARVIS
 */
public class Todo extends Task {

    public Todo(String taskDes, Priority priority, Frequency frequency, Set<Tag> tags) {
        super(taskDes, priority, frequency, tags);
    }

    public Todo(String taskDes) {
        this(taskDes, null, null, null);
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
