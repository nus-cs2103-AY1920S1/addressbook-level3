package seedu.address.profile.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents User's height in CM.
 */
public class Height {
    public static final String MESSAGE_CONSTRAINTS =
            "Height should only contain numeric characters and represented by centimeters.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[0-9]+";

    public final int height;

    public final String timestamp;

    /**
     * Constructs a {@code Height}.
     *
     * @param height A valid height.
     * @param datetime The timestamp of latest input of user's height.
     */
    public Height(String height, String datetime) {
        requireNonNull(height);
        checkArgument(isValidNumber(height), MESSAGE_CONSTRAINTS);
        this.height = Integer.parseInt(height);
        this.timestamp = datetime;
    }

    /**
     * Returns true if a given string is a valid number.
     */
    public static boolean isValidNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(height);
    }
}
