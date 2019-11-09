package dukecooks.model.workout.exercise.details;

import static java.util.Objects.requireNonNull;

/**
 * Represents the number of Sets of an exercise in Workout Planner.
 * Guarantees: immutable;
 */

public class Sets<Integer> extends ExerciseDetail {

    public Sets(java.lang.Integer sets) {
        requireNonNull(sets);
        super.magnitude = sets;
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Number of sets: ")
                .append(getMagnitude())
                .append("]");
        return builder.toString();
    }

    /**
     * Returns magnitude as an integer.
     */
    public int getMagnitudeAsInteger() {
        return (int) this.magnitude;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Sets)) {
            return false;
        }

        Sets otherSets = (Sets) other;
        return otherSets.getMagnitude().equals(getMagnitude());
    }

    @Override
    public int hashCode() {
        return magnitude.hashCode();
    }
}
