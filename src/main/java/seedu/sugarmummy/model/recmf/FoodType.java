package seedu.sugarmummy.model.recmf;

import seedu.sugarmummy.logic.parser.exceptions.ParseException;

/**
 * Represents all useful food types for categorizing food recommendations.
 */
public enum FoodType {

    NON_STARCHY_VEGETABLE("Non-starchy vegetable", "nsv"),
    STARCHY_VEGETABLE("Starchy vegetable", "sv"),
    FRUIT("Fruit", "f"),
    PROTEIN("Protein", "p"),
    SNACK("Snack", "s"),
    MEAL("Meal", "m");

    public static final String MESSAGE_CONSTRAINTS = "Food type should only be one of the following:\n"
            + getAllTypesInfo();

    private String typeName;
    private String abbr;

    FoodType(String typeName, String abbr) {
        this.typeName = typeName;
        this.abbr = abbr;
    }

    private static String getTypeInfo(FoodType type) {
        return type.abbr + "(" + type.typeName + ")";
    }

    public static String getAllTypesInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (FoodType type : FoodType.values()) {
            stringBuilder.append(getTypeInfo(type) + " ");
        }
        return stringBuilder.toString();
    }

    /**
     * Returns true if a given string is a valid food type abbreviation (Case Insensitive).
     */
    public static boolean isValidType(String abbr) {
        for (FoodType type : FoodType.values()) {
            String standardAbbr = type.getAbbr();
            if (abbr.equalsIgnoreCase(standardAbbr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the enum variable of {@code FoodType} based on the given string about the type abbreviation.
     */
    public static FoodType getFrom(String abbr) throws ParseException {
        for (FoodType type : FoodType.values()) {
            if (type.getAbbr().equalsIgnoreCase(abbr)) {
                return type;
            }
        }
        throw new ParseException(MESSAGE_CONSTRAINTS);
    }

    public String getTypeName() {
        return typeName;
    }

    public String getAbbr() {
        return abbr;
    }
}
