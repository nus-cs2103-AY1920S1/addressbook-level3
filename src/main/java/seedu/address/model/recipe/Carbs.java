package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a recipe's amount of carbohydrates.
 */
public class Carbs {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount of carbohydrates should only contain numbers, express in grams, with units omitted.";
    public static final String VALIDATION_REGEX = "\\d*";
    public final String value;
    public final String cardValue;

    /**
     * Constructs a {@Carbs}.
     *
     * @param carbs A valid amount of carbohydrates.
     */
    public Carbs(String carbs) {
        requireNonNull(carbs);
        checkArgument(isValidCarbs(carbs), MESSAGE_CONSTRAINTS);
        value = carbs;
        cardValue = "Carbs: " + value + "g";
    }

    /**
     * Returns true if given string is a valid amount of carbohydrates.
     */
    public static boolean isValidCarbs(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
}
