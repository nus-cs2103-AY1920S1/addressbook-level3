package seedu.address.model.listeners;

import java.util.List;

import seedu.address.model.tasks.TaskSource;

/**
 * Represents a listener that will be notified whenever the TaskList in ModelManager changes.
 */
public interface TaskListListener {

    void onTaskListChange(List<TaskSource> tasks);
}

