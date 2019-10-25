package seedu.jarvis.model.planner.tasks;

import java.time.LocalDate;
import java.util.Set;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;

/**
 * Represents a Deadline task in JARVIS
 */
public class Deadline extends Task {

    private LocalDate deadline;

    public Deadline(String taskDes, Priority priority, Frequency frequency, Set<Tag> tags, LocalDate deadline) {
        super(taskDes, priority, frequency, tags);
        this.deadline = deadline;
    }

    public Deadline(String taskDes, LocalDate deadline) {
        this(taskDes, null, null, null, deadline);
    }

    /**
     * Retrieves the due date of a deadline task
     * @return the LocalDate object that represents the due date
     */
    public LocalDate getDueDate() {
        return deadline;
    }

    @Override
    public String toString() {
        return "Deadline: " + this.taskDes + " by " + this.deadline
                + attributesString();
    }

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task && same description && same due date
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

        return isSameDes && isSameDate;
    }


}
