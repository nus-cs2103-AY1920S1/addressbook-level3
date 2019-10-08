package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents an Item's Task in ELISA.
 * Completion state of Event is false by default.
 * Priority of Event is medium by default.
 * Guarantees: immutable;
 */
public class Task {
    
    private final Priority priority;
    private final Boolean complete; 

    /**
     * Constructs a {@code Task}.
     *
     * @param priority A Priority of the event. Defaults to Priority.MEDIUM if null.
     * @param complete Denotes whether the task has been completed or not. Defaults to false if null.   
     */
    public Task(Priority priority, Boolean complete) {

        if (priority != null) {
            this.priority = priority;
        } else {
            this.priority = Priority.MEDIUM;
        }

        if (complete != null) {
            this.complete = complete;
        } else {
            this.complete = false;
        }
    }

    public Priority getPriority() {
        return priority;
    }

    public Boolean isComplete() {
        return complete;
    }
    
    public Task markComplete() {
        return new Task(getPriority(), true);
    }

    public Task markIncomplete() {
        return new Task(getPriority(), false);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Priority: ")
                .append(getPriority().toString())
                .append(" Completed: ")
                .append(isComplete().toString());
        return builder.toString();
    }

    //Problematic as the details of the task might be the same while the Item/actual task being referred to is not the
    //same due to description
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getPriority().equals(getPriority())
                && otherTask.isComplete().equals(isComplete());
    }

    //hashCode is problematic as I believe that there are only 3*2 permutations of priority and Boolean
    //Possibility of high number of hash collisions and as a result slower performance
    @Override
    public int hashCode() {
        return Objects.hash(priority, complete);
    }

}
