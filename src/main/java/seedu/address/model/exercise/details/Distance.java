package seedu.address.model.exercise.details;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.exercise.details.unit.DistanceUnit;


/**
 * Represents the Distance of an exercise in the Workout Planner.
 * Guarantees: immutable;
 */

public class Distance<Float> extends ExerciseDetail {

    private DistanceUnit unit;

    public Distance(float distance, DistanceUnit unit) {
        requireAllNonNull(distance, unit);
        super.magnitude = distance;
        this.unit = unit;
    }

    public DistanceUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Distance: ")
                .append(getMagnitude())
                .append(getUnit())
                .append(']');
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Distance)) {
            return false;
        }

        Distance otherDistance = (Distance) other;
        return otherDistance.getMagnitude().equals(getMagnitude())
                && otherDistance.getUnit().equals(getUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(magnitude, unit);
    }
}
