package seedu.algobase.model.attempt;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.algobase.model.problem.Problem;

/**
 * Represents an Attempt in the algobase.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Attempt {

    private final Problem problem;
    private final Boolean isSolved;
    private final LocalDateTime dateTime;

    /**
     * Every field must be present and not null.
     */
    public Attempt(Problem problem, Boolean isSolved, LocalDateTime dateTime) {
        requireAllNonNull(problem, isSolved, dateTime);
        this.problem = problem;
        this.isSolved = isSolved;
        this.dateTime = dateTime;
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
     * Returns true if both attempts have the same fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Attempt)) {
            return false;
        }

        Attempt otherAttempt = (Attempt) other;
        return otherAttempt.getProblem().equals(getProblem())
            && otherAttempt.getIsSolved().equals(getIsSolved())
            && otherAttempt.getDateTime().equals(getDateTime());
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
