package seedu.address.model.calendar.task;

import java.util.Set;

import seedu.address.model.calendar.tag.TaskTag;

/**
 * Represents a ToDoTask in the CalendarAddressbook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ToDoTask extends Task {

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
    public ToDoTask(TaskTitle taskTitle, TaskDay taskDay, TaskDescription taskDescription, TaskDeadline taskDeadline,
                    TaskTime taskTime, Set<TaskTag> taskTags, int week) {
        super(taskTitle, taskDay, taskDescription, taskDeadline, taskTime, taskTags, week, false);
    }
}
