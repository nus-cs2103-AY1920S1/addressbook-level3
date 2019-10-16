package seedu.address.model.details;

import static java.util.Objects.requireNonNull;

/**
 * Represents the number of reps of an exercise in the Workout planner.
 * Guarantees: immutable;
 */

public class Repetitions<Integer> extends ExerciseDetail {

    public Repetitions(java.lang.Integer repetitions) {
        requireNonNull(repetitions);
        super.magnitude = repetitions;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Number of repetitions: ")
                .append(getMagnitude())
                .append("]");
        return builder.toString();
    }
}
