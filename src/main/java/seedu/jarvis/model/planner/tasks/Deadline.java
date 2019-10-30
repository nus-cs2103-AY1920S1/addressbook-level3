package seedu.jarvis.model.planner.tasks;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.enums.Status;
import seedu.jarvis.model.planner.enums.TaskType;
import seedu.jarvis.storage.planner.JsonAdaptedDeadline;
import seedu.jarvis.storage.planner.JsonAdaptedTask;

/**
 * Represents a Deadline task in JARVIS
 */
public class Deadline extends Task {

    private LocalDate deadline;

    public Deadline(String taskDes, Priority priority, Frequency frequency, Status status, Set<Tag> tags,
                    LocalDate deadline) {
        super(taskDes, priority, frequency, status, tags);
        this.deadline = deadline;
    }

    public Deadline(String taskDes, LocalDate deadline) {
        this(taskDes, null, null, Status.NOT_DONE, new HashSet<>(), deadline);
    }

    /**
     * Retrieves the due date of a deadline task
     * @return the LocalDate object that represents the due date
     */
    public LocalDate getDueDate() {
        return deadline;
    }

    /**
     * Gets the {@code JsonAdaptedTask} for this task.
     *
     * @return {@code JsonAdaptedTask}.
     */
    @Override
    public JsonAdaptedTask adaptToJsonAdaptedTask() {
        return new JsonAdaptedDeadline(this);
    }

    @Override
    public String toString() {
        return "Deadline: " + this.taskDes + " by " + this.deadline
                + attributesString();
    }

    /**
     * Retrieves the {@code TaskType} of the task
     * @return {@TaskType} of the task
     */
    @Override
    public TaskType getTaskType() {
        return TaskType.Deadline;
    }

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task, same description, same due date,
     * and same status
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    @Override
    public boolean equals(Object other) {

        if (!(other instanceof Deadline)) {
            return false;
        }

        boolean isSameDes = taskDes.equals(((Task) other).taskDes);
        Deadline dOther = (Deadline) other;
        boolean isSameDate = deadline.compareTo(dOther.getDueDate()) == 0;
        boolean isSameStatus = status.equals(((Deadline) other).getStatus());

        return isSameDes && isSameDate && isSameDate;
    }


}
