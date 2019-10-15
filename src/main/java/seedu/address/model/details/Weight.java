package seedu.address.model.details;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.details.unit.WeightUnit;


/**
 * Represents the Weight used in an exercise in the Workout Planner.
 * Guarantees: immutable;
 */

public class Weight<Float> extends ExerciseDetail {

    private WeightUnit unit;

    public Weight(float weight, WeightUnit unit) {
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
}
