package seedu.exercise.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.core.ValidationRegex.ONLY_NUMBERS;
import static seedu.exercise.commons.util.AppUtil.checkArgument;

/**
 * Represents the estimated amount of calories burnt in an exercise.
 * Guarantees: immutable; is valid as declared in {@link #isValidCalories(String)}
 */
public class Calories {
    public static final String PROPERTY_CALORIES = "Calories";
    public static final String MESSAGE_CONSTRAINTS = "Calories should only contain numbers and "
            + "should be less than or equal to 50,000";
    public final String value;

    /**
     * Constructs a {@code Calories}.
     *
     * @param calories A valid calories burnt.
     */
    public Calories(String calories) {
        requireNonNull(calories);
        checkArgument(isValidCalories(calories), MESSAGE_CONSTRAINTS);
        value = removeLeadingZeros(calories);
    }

    /**
     * Returns true if a given string is a valid calories burnt.
     */
    public static boolean isValidCalories(String test) {
        return test.matches(ONLY_NUMBERS) && Double.parseDouble(test) <= 50000;
    }

    private String removeLeadingZeros(String calories) {
        return calories.replaceFirst("^0*", "");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Calories // instanceof handles nulls
            && value.equals(((Calories) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
