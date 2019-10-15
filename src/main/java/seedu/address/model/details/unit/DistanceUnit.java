package seedu.address.model.details.unit;

public enum DistanceUnit {
    METER,
    KILOMETER;

    public String toJson(){
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