package dukecooks.model.workout.exercise.details;

import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import dukecooks.model.workout.exercise.details.unit.WeightUnit;

/**
 * Represents the Weight used in an exercise in the Workout Planner.
 * Guarantees: immutable;
 */

public class ExerciseWeight<Float> extends ExerciseDetail {

    private WeightUnit unit;

    public ExerciseWeight(java.lang.Float weight, WeightUnit unit) {
        requireAllNonNull(weight, unit);
        super.magnitude = weight;
        this.unit = unit;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Weight: ")
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

        if (!(other instanceof ExerciseWeight)) {
            return false;
        }

        ExerciseWeight otherWeight = (ExerciseWeight) other;
        return otherWeight.getMagnitude().equals(getMagnitude())
                && otherWeight.getUnit().equals(getUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(magnitude, unit);
    }
}
