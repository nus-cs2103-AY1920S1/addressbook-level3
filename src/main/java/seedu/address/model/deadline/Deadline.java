package seedu.address.model.deadline;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;


/**
 * Represents a FlashCard in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deadline {

    public static final String MESSAGE_CONSTRAINTS = "Deadlines are of dd/MM/yyyy Format.";

    // ADDED IN THE FUTURE - for comparison of deadlines for each flashcard
    //public static LocalDate today = LocalDate.now();
    //public static String dateStr;
    //public static LocalDate localDate;

    // Identity fields
    private static Task task;
    private static DueDate dueDate;

    public Deadline(Task task, DueDate dueDate) {
        requireAllNonNull(task, dueDate);
        this.task = task;
        this.dueDate = dueDate;
    }

    public Task getTask() {
        return task;
    }

    public DueDate getDueDate() {
        return dueDate;
    }

    public void setTask(Task newTask) {
        task = newTask;
    }

    public void setDueDate(DueDate newDate) {
        dueDate = newDate;
    }

    /**
     * Returns true if both deadlines of the same task have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two deadlines.
     */
    public boolean isSameDeadline(Deadline otherDeadline) {
        if (otherDeadline == this) {
            return true;
        }
        return otherDeadline != null
                && otherDeadline.getTask().equals(getTask())
                && (otherDeadline.getDueDate().equals(getDueDate()));
    }

    /**
     * Returns true if both Deadlines have the same identity and data fields.
     * This defines a stronger notion of equality between two Deadlines.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherDeadline = (Deadline) other;
        return otherDeadline.getTask().equals(getTask())
                && otherDeadline.getDueDate().equals(getDueDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(task, dueDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTask())
                .append(" Due Date: ")
                .append(getDueDate());
        return builder.toString();
    }

}
