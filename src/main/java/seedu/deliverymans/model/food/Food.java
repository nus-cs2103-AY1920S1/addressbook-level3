package seedu.deliverymans.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food in a restaurant.
 */
public class Food {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String foodName;

    /**
     * Constructs a {@code Food}.
     *
     * @param foodName A valid food name.
     */
    public Food(String foodName) {
        requireNonNull(foodName);
        checkArgument(isValidFoodName(foodName), MESSAGE_CONSTRAINTS);
        this.foodName = foodName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidFoodName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Food // instanceof handles nulls
                && foodName.equals(((Food) other).foodName)); // state check
    }

    @Override
    public int hashCode() {
        return foodName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + foodName + ']';
    }

}
