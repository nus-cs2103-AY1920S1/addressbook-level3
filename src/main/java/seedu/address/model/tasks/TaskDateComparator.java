package seedu.address.model.tasks;

import java.util.Comparator;

/**
 * Represents a Comparator to compare the events by their date, followed by their name in lexicographic order.
 */
public class TaskDateComparator implements Comparator<TaskSource> {

    @Override
    public int compare(TaskSource task, TaskSource otherTask) {
        if (task.isDone() == otherTask.isDone()) {
            if (task.getDueDate() != null && otherTask.getDueDate() != null) {
                if (task.getDueDate().compareTo(otherTask.getDueDate()) == 0) {
                    return task.getDescription().compareTo(otherTask.getDescription());
                } else {
                    return task.getDueDate().compareTo(otherTask.getDueDate());
                }
            } else {
                return task.getDescription().compareTo(otherTask.getDescription());
            }

        } else {
            return Boolean.compare(task.isDone(), otherTask.isDone());
        }

    }
}
