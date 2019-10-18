package seedu.address.model.exercise.details.unit;

/**
 * Represents the unit used for the Distance object.
 *
 */

public enum DistanceUnit {
    METER,
    KILOMETER;

    public String toJson() {
        return name();
    }

    @Override
    public String toString() {
        if (this == DistanceUnit.METER) {
            return "m";
        } else {
            return "km";
        }
    }
}
