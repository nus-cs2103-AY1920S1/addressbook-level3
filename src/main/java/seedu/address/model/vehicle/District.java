package seedu.address.model.vehicle;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's  in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDistrict(int)}
 */
public class District {

    public static final String MESSAGE_CONSTRAINTS =
            "Districts should only contain alphanumeric characters and spaces, and it should not be blank";

    public final int district;

    /**
     * Constructs a {@code District}.
     *
     * @param d A valid District.
     */
    public District(int d) {
        requireNonNull(d);
        checkArgument(isValidDistrict(d), MESSAGE_CONSTRAINTS);
        district = d;
    }

    /**
     * Returns true if a given string is a valid District.
     */
    public static boolean isValidDistrict(int test) {
        return 1 <= test && test <= 28;
    }


    @Override
    public String toString() {
        return String.valueOf(district);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof District // instanceof handles nulls
                && district == ((District) other).district); // state check
    }

}

