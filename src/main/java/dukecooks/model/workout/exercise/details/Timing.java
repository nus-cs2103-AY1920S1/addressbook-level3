package dukecooks.model.workout.exercise.details;

import static java.util.Objects.requireNonNull;

import java.time.Duration;

/**
 * Represents the Timing of an exercise in the Workout Planner.
 * Guarantees: immutable;
 */
public class Timing extends ExerciseDetail {

    private final Duration duration;

    public Timing(Duration time) {
        requireNonNull(time);
        super.magnitude = time;
        duration = time;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600, (
                        absSeconds % 3600) / 60,
                absSeconds % 60);
        return "[Timing: " + positive + "]";
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
