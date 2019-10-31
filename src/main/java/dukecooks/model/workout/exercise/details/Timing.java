package dukecooks.model.workout.exercise.details;

import static java.util.Objects.requireNonNull;

/**
 * Represents the Timing of an exercise in the Workout Planner.
 * Guarantees: immutable;
 */
public class Timing<Duration> extends ExerciseDetail {

    public Timing(Duration time) {
        requireNonNull(time);
        super.magnitude = time;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Timing: ")
                .append(getMagnitude().toString().replace("PT", "").replace("H", " hours ")
                        .replace("M", " minutes ").replace("S", " seconds"))
                .append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Timing)) {
            return false;
        }

        Timing otherTiming = (Timing) other;
        return otherTiming.getMagnitude().equals(getMagnitude());
    }

    @Override
    public int hashCode() {
        return magnitude.hashCode();
    }
}
