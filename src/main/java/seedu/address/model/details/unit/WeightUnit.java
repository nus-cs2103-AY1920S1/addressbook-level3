package seedu.address.model.details.unit;

public enum WeightUnit {
    GRAM,
    KILOGRAM,
    POUND;

    public String toJson(){
        return name();
    }

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
