package seedu.module.model.module;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Module's deadline in the module book.
 * Guarantees: immutable; is always valid
 */
public class Deadline {
    public static final String MESSAGE_CONSTRAINTS = "Not a valid Deadline";

    private String description;
    private String time;
    private boolean isDone;
    private boolean isInProgress;
    private String tag;
    private int deadlineListNum = 0;

    public Deadline(String description, String time, String tag) {
        requireNonNull(description);
        this.description = description;
        this.time = time;
        this.tag = tag;
    }

    /**
     * Marks the deadline task as done.
     */
    public void markAsDone() {
        isDone = true;
        isInProgress = false;
    }

    /**
     * Marks the deadline task as in progress.
     */
    public void markAsInProgress() {
        isInProgress = true;
        isDone = false;
    }

    public String getStatus() {
        if (isDone) {
            return "" + "\u2713";
        } else if (isInProgress) {
            return "-";
        } else {
            return " ";
        }
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getTag() {
        return tag;
    }

    public void setValue(String newValue) {
        this.description = newValue;
    }

    public void editDescription(String newDescription) {
        this.description = newDescription;
    }

    public void editTime(String newTime) {
        this.time = newTime;
    }

    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description + " ," + time + " PRIORITY: " + tag;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && description.equals(((Deadline) other).description))
                && time.equals(((Deadline) other).time); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
