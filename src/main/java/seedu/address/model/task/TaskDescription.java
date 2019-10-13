package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

public class TaskDescription {
    public final String fullTaskDescription;

    /**
     * Constructs a {@code TaskDescription}.
     *
     * @param taskDescription A valid description.
     */
    public TaskDescription(String taskDescription) {
        requireNonNull(taskDescription);
        fullTaskDescription = taskDescription;
    }

    @Override
    public String toString() {
        return fullTaskDescription + "\n";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDescription // instanceof handles nulls
                && fullTaskDescription.equals(((TaskDescription) other).fullTaskDescription)); // state check
    }

    @Override
    public int hashCode() {
        return fullTaskDescription.hashCode();
    }

}
