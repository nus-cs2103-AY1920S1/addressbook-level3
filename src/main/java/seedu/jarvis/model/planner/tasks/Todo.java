package seedu.jarvis.model.planner.tasks;

import java.util.Set;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;

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

    @Override
    public String toString() {
        return "Todo: " + this.taskDes + attributesString();
    }

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task && same description
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Todo)) {
            return false;
        }

        return taskDes.equals(((Todo) other).taskDes);
    }
}
