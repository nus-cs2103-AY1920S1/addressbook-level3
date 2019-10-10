package seedu.sgm.model.food;

/**
 * Represents all useful food types for categorizing food recommendations.
 */
public enum FoodType {
    NON_STARCHY_VEGETABLE,
    STARCHY_VEGETABLE,
    FRUIT,
    PROTEIN,
    SNACK,
    MEAL;

    public static FoodType getFrom(String shortHandType) {
        switch (shortHandType.toLowerCase()) {
        case "nsv":
            return NON_STARCHY_VEGETABLE;
        case "sv":
            return STARCHY_VEGETABLE;
        case "f":
            return FRUIT;
        case "p":
            return PROTEIN;
        case "s":
            return SNACK;
        case "m":
            return MEAL;
        default:
            //TODO: throw exception!
            return null;
        }
    }

}
