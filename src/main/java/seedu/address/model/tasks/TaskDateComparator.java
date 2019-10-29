package seedu.address.model.tasks;

import java.util.Comparator;

/**
 * Represents a Comparator to compare the events by their date, followed by their name in lexicographic order.
 */
public class TaskDateComparator implements Comparator<TaskSource> {

    @Override
    public int compare(TaskSource task, TaskSource otherTask) {
        if(task.isCompleted() == otherTask.isCompleted()) {
            if (task.getDueDate().compareTo(otherTask.getDueDate()) == 0) {
                return task.getDescription().compareTo(otherTask.getDescription());
            } else {
                return task.getDueDate().compareTo(otherTask.getDueDate());
            }
        } else {
            return Boolean.compare(task.isCompleted(), otherTask.isCompleted());
        }

    }
}
