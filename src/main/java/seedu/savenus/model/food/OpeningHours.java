package seedu.savenus.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Represents the opening hours a particular food item.
 */
public class OpeningHours implements Field {

    // This to show the user how they should type the opening hours.
    public static final String FORMAT_CONSTRAINTS =
            "Invalid opening hours provided\n"
          + "The format should be in the form of HHMM HHMM";

    public static final String COMPARISON_CONSTRAINTS =
            "Invalid opening hours provided\n"
                    + "The ending time should not be before the starting time";

    public static final String DEFAULT_VALUE = "No Opening Hours";

    // The first character must not be a whitespace. Otherwise " " becomes a valid input.
    // The format is exactly HHMM HHMM.
    public static final String VALIDATION_REGEX = "([0-1][0-9][0-5][0-9]|[0-2][0-3][0-5][0-9]|2400) "
        + "([0-1][0-9][0-5][0-9]|[0-2][0-3][0-5][0-9]|2400)";
    public final String openingHours;

    /**
     * Constructs a {@code OpeningHours}
     *
     * @param hours Valid opening hour format.
     */
    public OpeningHours(String hours) {
        requireNonNull(hours);
        checkArgument(isValidOpeningHours(hours), FORMAT_CONSTRAINTS);
        openingHours = hours;
    }

    /**
     * Check validity of input opening hours format.
     *
     * @return True if valid, false otherwise.
     */
    public static boolean isValidOpeningHours(String hours) {
        if (hours.equals(OpeningHours.DEFAULT_VALUE)) {
            return true;
        } else {
            String[] separateHours = hours.split(" ");
            return hours.matches(VALIDATION_REGEX);
        }
    }

    /**
     * Check validity of input opening hours comparison.
     *
     * @return True if valid, false otherwise.
     */
    public static boolean isValidComparison(String hours) {
        if (hours.equals(OpeningHours.DEFAULT_VALUE)) {
            return true;
        } else {
            String[] separateHours = hours.split(" ");
            return separateHours[0].compareTo(separateHours[1]) <= 0;
        }
    }


    /**
     * Gets the field as a String.
     * @return a String representation of the field.
     */
    public String getField() {
        return this.toString();
    }

    @Override
    public String toString() {
        return openingHours;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object.
                || (other instanceof OpeningHours // To handle nulls
                && openingHours.equals(((OpeningHours) other).openingHours)); // Checks the state.
    }

    @Override
    public int hashCode() {
        return openingHours.hashCode();
    }

    @Override
    public int compareTo(Field other) {
        OpeningHours otherOpeningHours = (OpeningHours) other;
        if (otherOpeningHours == null) {
            return 1;
        } else {
            return this.getField().compareTo(otherOpeningHours.getField());
        }
    }
}
