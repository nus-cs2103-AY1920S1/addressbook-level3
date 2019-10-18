package seedu.address.model.exercise.details.unit;

/**
 * Represents the unit used for a Weight object.
 *
 */
public enum WeightUnit {
    GRAM,
    KILOGRAM,
    POUND;

    public String toJson() {
        return name();
    }

    @Override
    public String toString() {
        if (this == WeightUnit.GRAM) {
            return "g";
        } else if (this == WeightUnit.KILOGRAM) {
            return "kg";
        } else {
            return "lbs";
        }
    }
}
