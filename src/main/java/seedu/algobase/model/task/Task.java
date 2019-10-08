package seedu.algobase.model.task;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.algobase.model.problem.Problem;

/**
 * Represents a Task in the algobase.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    private final Problem problem;
    private final Boolean isSolved;
    private final LocalDateTime dateTime;

    /**
     * Problem field must be present and not null.
     */
    public Task(Problem problem) {
        requireAllNonNull(problem);
        this.problem = problem;
        this.isSolved = false;
        this.dateTime = null;
    }

    public Problem getProblem() {
        return problem;
    }

    public Boolean getIsSolved() {
        return isSolved;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns true if both tasks have the same fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getProblem().equals(getProblem())
                && otherTask.getIsSolved().equals(getIsSolved())
                && otherTask.getDateTime().equals(getDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(problem, isSolved, dateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Problem: ")
            .append(getProblem())
            .append(" Date: ")
            .append(getDateTime())
            .append(" isSolved: ")
            .append(getIsSolved());
        return builder.toString();
    }

}
