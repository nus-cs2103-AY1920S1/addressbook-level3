package seedu.address.model.recipe.components;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a recipe's amount of protein.
 */
public class Protein {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount of protein should only contain numbers, express in g, with units omitted.";
    public static final String VALIDATION_REGEX = "\\d*";
    public final String value;
    public final String cardValue;

    /**
     * Constructs a {@Protein}.
     *
     * @param protein A valid amount of protein.
     */
    public Protein(String protein) {
        requireNonNull(protein);
        checkArgument(isValidProtein(protein), MESSAGE_CONSTRAINTS);
        value = protein;
        cardValue = "Protein: " + value + "g";
    }

    /**
     * Returns true if given string is a valid amount of protein.
     */
    public static boolean isValidProtein(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
}
