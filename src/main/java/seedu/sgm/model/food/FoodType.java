package seedu.sgm.model.food;

import static seedu.address.logic.parser.CliSyntax.ABBR_FRUIT;
import static seedu.address.logic.parser.CliSyntax.ABBR_MEAL;
import static seedu.address.logic.parser.CliSyntax.ABBR_NON_STARCHY_VEGETABLE;
import static seedu.address.logic.parser.CliSyntax.ABBR_PROTEIN;
import static seedu.address.logic.parser.CliSyntax.ABBR_SNACK;
import static seedu.address.logic.parser.CliSyntax.ABBR_STARCHY_VEGETABLE;
import static seedu.address.logic.parser.CliSyntax.FLAG_SIGNAL;

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
        case ABBR_NON_STARCHY_VEGETABLE:
        case FLAG_SIGNAL + ABBR_NON_STARCHY_VEGETABLE:
            return NON_STARCHY_VEGETABLE;
        case ABBR_STARCHY_VEGETABLE:
        case FLAG_SIGNAL + ABBR_STARCHY_VEGETABLE:
            return STARCHY_VEGETABLE;
        case ABBR_FRUIT:
        case FLAG_SIGNAL + ABBR_FRUIT:
            return FRUIT;
        case ABBR_PROTEIN:
        case FLAG_SIGNAL + ABBR_PROTEIN:
            return PROTEIN;
        case ABBR_SNACK:
        case FLAG_SIGNAL + ABBR_SNACK:
            return SNACK;
        case ABBR_MEAL:
        case FLAG_SIGNAL + ABBR_MEAL:
            return MEAL;
        default:
            return null;
        }
    }

}
