package seedu.jarvis.model.planner.tasks;

import java.util.Calendar;

/**
 * Represents a Deadline task in JARVIS
 */
public class Deadline extends Task {

    private Calendar deadline;

    public Deadline(String taskDes, Calendar deadline) {
        super(taskDes);
        this.deadline = deadline;
    }

    /**
     * Retrieves the due date of a deadline task
     * @return the calendar object that represents the due date
     */
    public Calendar getDueDate() {
        return deadline;
    }

    @Override
    public String toString() {
        return "Deadline: " + this.taskDes + " by " + this.deadline;
    }

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task && same description && same due date
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    @Override
    protected boolean isEqual(Task other) {
        //TODO
        boolean isSameType = other instanceof Deadline;
        boolean isSameDes = taskDes.equals(other.taskDes);
        boolean isSameDate = false;
        if (isSameType) {
            Deadline dOther = (Deadline) other;
            isSameDate = deadline.compareTo(dOther.getDueDate()) == 0;
        }

        return isSameType && isSameDes && isSameDate;
    }


}
