package seedu.sgm.model.food;

import static seedu.address.logic.parser.CliSyntax.ABBR_FRUIT;
import static seedu.address.logic.parser.CliSyntax.ABBR_MEAL;
import static seedu.address.logic.parser.CliSyntax.ABBR_NON_STARCHY_VEGETABLE;
import static seedu.address.logic.parser.CliSyntax.ABBR_PROTEIN;
import static seedu.address.logic.parser.CliSyntax.ABBR_SNACK;
import static seedu.address.logic.parser.CliSyntax.ABBR_STARCHY_VEGETABLE;
import static seedu.address.logic.parser.CliSyntax.FLAG_SIGNAL;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents all useful food types for categorizing food recommendations.
 */
public enum FoodType {
    NON_STARCHY_VEGETABLE("Non-starchy vegetable", ABBR_NON_STARCHY_VEGETABLE),
    STARCHY_VEGETABLE("Starchy vegetable", ABBR_STARCHY_VEGETABLE),
    FRUIT("Fruit", ABBR_FRUIT),
    PROTEIN("Protein", ABBR_PROTEIN),
    SNACK("Snack", ABBR_SNACK),
    MEAL("Meal", ABBR_MEAL);

    private String type;
    private String shortHand;

    public static final String MESSAGE_CONSTRAINTS = "Food types should only be one of the following"
        + "nsv(Non-starchy vegetable); sv(Starchy vegetable); f(Fruit); p(Protein); s(Snack); m(Meal) ";

    FoodType(String type, String shortHand) {
        this.type = type;
        this.shortHand = shortHand;
    }


    public String getTypeName() {
        return type;
    }

    public String getShortHand() {
        return shortHand;
    }

    public static FoodType getFrom(String shortHandType) throws ParseException {
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
            throw new ParseException("Invalid food type");
        }
    }
}
