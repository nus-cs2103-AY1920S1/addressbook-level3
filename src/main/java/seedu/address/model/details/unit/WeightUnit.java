package seedu.address.model.details.unit;

public enum WeightUnit {
    GRAM,
    KILOGRAM,
    POUND;

    @Override
    public String toString() {
        if (this == WeightUnit.GRAM) {
            return "g";
        } else if (this == WeightUnit.KILOGRAM){
            return "kg";
        } else {
            return "lbs";
        }
    }
}
