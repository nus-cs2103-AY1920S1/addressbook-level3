package dukecooks.model.workout.exercise.details;

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
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Number of repetitions: ")
                .append(getMagnitude())
                .append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Repetitions)) {
            return false;
        }

        Repetitions otherReps = (Repetitions) other;
        return otherReps.getMagnitude().equals(getMagnitude());
    }

    @Override
    public int hashCode() {
        return magnitude.hashCode();
    }
}
