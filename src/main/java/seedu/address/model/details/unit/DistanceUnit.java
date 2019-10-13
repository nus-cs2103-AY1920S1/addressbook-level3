package seedu.address.model.details.unit;

public enum DistanceUnit {
    METER,
    KILOMETER;

    @Override
    public String toString() {
        if (this == DistanceUnit.METER) {
            return "m";
        } else {
            return "km";
        }
    }
}