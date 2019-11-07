package seedu.address.model.calendar.task;

import java.util.Set;

import seedu.address.model.calendar.tag.TaskTag;


/**
 * Represents a ModuleTask in the CalendarAddressbook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ModuleTask extends Task {

    /**
     * Every field must be present and not null.
     *
     * @param taskTitle
     * @param taskDay
     * @param taskDescription
     * @param taskDeadline
     * @param taskTime
     * @param taskTags
     * @param week
     */
    public ModuleTask(TaskTitle taskTitle, TaskDay taskDay, TaskDescription taskDescription,
                      TaskDeadline taskDeadline, TaskTime taskTime, Set<TaskTag> taskTags, int week) {
        super(taskTitle, taskDay, taskDescription, taskDeadline, taskTime, taskTags, week, true);
    }

    /**
     * Returns true if both task of the same taskTitle have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    @Override
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
            && otherTask.getTaskTitle().equals(getTaskTitle())
            && otherTask.getTaskTime().equals(otherTask.getTaskTime())
            && (otherTask.getTaskDay().equals(getTaskDay()));
    }

}
