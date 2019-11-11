package seedu.sugarmummy.model.recmf;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents the name of <code>Food</code> in the recommended food list. Guarantees: immutable; is valid as declared in
 * {@link #isValidName(String)}
 */
public class FoodName implements Comparable<FoodName> {

    public static final String MESSAGE_CONSTRAINTS = "Names should only contain alphabets, numbers, and whitespace. "
            + "It should not be blank or longer than 30 characters";

    /*
     * The first character of the food must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String foodName;

    /**
     * Constructs a {@code FoodName}.
     *
     * @param foodName a valid food name
     */
    public FoodName(String foodName) {
        requireNonNull(foodName);
        checkArgument(isValidName(foodName), MESSAGE_CONSTRAINTS);
        this.foodName = foodName;
    }

    /**
     * Returns true if a given string is a valid food name. A name is considered valid if it does not contain special
     * characters and its length is less than 30 characters.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= 30;
    }

    public String getFoodName() {
        return foodName;
    }

    @Override
    public String toString() {
        return foodName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FoodName)) {
            return false;
        }
        return foodName.equalsIgnoreCase(((FoodName) other).foodName);
    }

    @Override
    public int hashCode() {
        return foodName.hashCode();
    }

    @Override
    public int compareTo(FoodName another) {
        return foodName.compareTo(another.foodName);
    }
}
