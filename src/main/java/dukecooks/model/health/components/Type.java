package dukecooks.model.health.components;

import static java.util.Objects.requireNonNull;

import java.util.EnumSet;

/**
 * Represents the type of health record.
 */
public enum Type {
    Glucose("mmol/L", 0, 50, "latest"),
    Height("cm", 50, 300, "latest"),
    Weight("kg", 10, 400, "latest"),
    Calories("kcal", 0, 100000, "sum"),
    Carbs("g", 0, 1000, "sum"),
    Protein("g", 0, 1000, "sum"),
    Fats("g", 0, 1000, "sum");

    private final String unit;
    private final double lowerLimit;
    private final double upperLimit;
    private final String behavior;

    Type(String unit, double lowerLimit, double upperLimit, String behavior) {
        this.unit = unit;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.behavior = behavior;
    }

    public String getType() {
        return name();
    }

    public String getUnit() {
        return unit;
    }

    public String getBehavior() {
        return behavior;
    }

    public static EnumSet<Type> getTypeSet() {
        return EnumSet.allOf(Type.class);
    }

    /**
     * Checks if the String is one of the health types.
     */
    public static boolean isValidType(String str) {
        requireNonNull(str);
        for (Type type: Type.values()) {
            if (type.name().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if value if within the upper and lower bound of given type
     * This method should be called after @exists method.
     */
    public static boolean isValidNumber(String type, double value) {
        return value >= Type.valueOf(type).lowerLimit
                && value <= Type.valueOf(type).upperLimit;
    }

    @Override
    public String toString() {
        return name();
    }

    public static String messageConstraints() {
        return "Record type should only contain alphanumeric characters and spaces, and it should not be blank";
    }

    public String messageInflatedValue() {
        return name() + " should be within " + lowerLimit + unit + " to " + upperLimit + unit;
    }

}
