package seedu.address.model.tasks;

import java.util.Comparator;

//@@author Kyzure
/**
 * Represents a Comparator to compare the events by their date, followed by their name in lexicographic order.
 */
public class TaskDateComparator implements Comparator<TaskSource> {

    @Override
    public int compare(TaskSource task, TaskSource otherTask) {
        // If both have are done.
        if (task.isDone() == otherTask.isDone()) {
            if (task.getDueDate() == null && otherTask.getDueDate() == null) {
                // If both don't have due date.
                return task.getDescription().compareTo(otherTask.getDescription());
            } else if (task.getDueDate() == null && otherTask.getDueDate() != null) {
                return 1;
            } else if (task.getDueDate() != null && otherTask.getDueDate() == null) {
                return -1;
            } else {
                if (task.getDueDate().equals(otherTask.getDueDate())) {
                    return task.getDescription().compareTo(otherTask.getDescription());
                } else {
                    return task.getDueDate().compareTo(otherTask.getDueDate());
                }
            }
        } else {
            return Boolean.compare(task.isDone(), otherTask.isDone());
        }

    }
}
