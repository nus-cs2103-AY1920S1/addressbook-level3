package organice.model.person;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's age in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class Age {


    public static final String MESSAGE_CONSTRAINTS =
            "Age should only be an integer more than 0 and less than 150.";
    public static final Integer AGE_MAX = 150;
    public static final Integer AGE_MIN = 0;
    public final String value;

    /**
     * Constructs a {@code Age}.
     *
     * @param age A valid age.
     */
    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        value = age;
    }

    /**
     * Returns true if a given string is a valid age.
     */
    public static boolean isValidAge(String test) {
        try {
            Integer age = Integer.parseInt(test);
            if (age > AGE_MIN && age < AGE_MAX) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Age // instanceof handles nulls
                && value.equals(((Age) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
