package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a recipe's amount of fats.
 */
public class Fats {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount of fats should only contain numbers, express in g, with units omitted.";
    public static final String VALIDATION_REGEX = "\\d*";
    public final String value;

    /**
     * Constructs a {@Fats}.
     *
     * @param fats A valid amount of fats.
     */
    public Fats(String fats) {
        requireNonNull(fats);
        checkArgument(isValidFats(fats), MESSAGE_CONSTRAINTS);
        value = fats;
    }

    /**
     * Returns true if given string is a valid amount of fats.
     */
    public static boolean isValidFats(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
}
