package seedu.address.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the opening hours a particular food item.
 */
public class OpeningHours {

    // This to show the user how they should type the opening hours.
    public static final String MESSAGE_CONSTRAINTS =
            "Opening hours should only contain numbers and spaces, and it should not be blank. "
          + "The format should be in the form of HHMM HHMM";

    public static final String DEFAULT_VALUE = "No Opening Hours";

    // The first character must not be a whitespace. Otherwise " " becomes a valid input.
    // The format is exactly HHMM HHMM.
    public static final String VALIDATION_REGEX = "\\d{4} \\d{4}";

    public final String openingHours;

    /**
     * Constructs a {@code OpeningHours}
     *
     * @param hours Valid opening hour format.
     */
    public OpeningHours(String hours) {
        requireNonNull(hours);
        checkArgument(isValidOpeningHours(hours), MESSAGE_CONSTRAINTS);
        openingHours = hours;
    }

    /**
     * Checking whether the opening hours is valid or not.
     *
     * @return True if valid, false otherwise.
     */
    public static boolean isValidOpeningHours(String hours) {
        if (hours.equals(OpeningHours.DEFAULT_VALUE)) {
            return true;
        } else {
            return hours.matches(VALIDATION_REGEX);
        }
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
}
