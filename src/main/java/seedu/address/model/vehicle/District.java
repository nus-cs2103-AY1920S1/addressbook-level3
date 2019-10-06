package seedu.address.model.vehicle;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's  in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDistrict(String)}
 */
public class District {

    public static final String MESSAGE_CONSTRAINTS =
            "Districts should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String district;

    /**
     * Constructs a {@code District}.
     *
     * @param d A valid District.
     */
    public District(String d) {
        requireNonNull(d);
        checkArgument(isValidDistrict(d), MESSAGE_CONSTRAINTS);
        district = d;
    }

    /**
     * Returns true if a given string is a valid District.
     */
    public static boolean isValidDistrict(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return district;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof District // instanceof handles nulls
                && district.equals(((District) other).district)); // state check
    }

    @Override
    public int hashCode() {
        return district.hashCode();
    }

}

