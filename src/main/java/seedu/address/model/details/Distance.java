package seedu.address.model.details;

import seedu.address.model.details.unit.DistanceUnit;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Distance<Float> extends ExerciseDetail {

    private DistanceUnit unit;

    public Distance(float distance, DistanceUnit unit){
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
}
