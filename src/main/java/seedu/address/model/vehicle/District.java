package seedu.address.model.vehicle;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's  in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDistrict(int)}
 */
public class District {

    public static final String MESSAGE_CONSTRAINTS =
            "Districts should be a single integer from 1 to 28";

    public static final int FIRST_DISTRICT = 1;
    public static final int LAST_DISTRICT = 28;

    private final int districtNum;

    /**
     * Constructs a {@code District}.
     *
     * @param d A valid District.
     */
    public District(int d) {
        checkArgument(isValidDistrict(d), MESSAGE_CONSTRAINTS);
        districtNum = d;
    }

    public int getDistrictNum() {
        return this.districtNum;
    }

    /**
     * Returns true if a given string is a valid District.
     */
    public static boolean isValidDistrict(int test) {
        return FIRST_DISTRICT <= test && test <= LAST_DISTRICT;
    }


    @Override
    public String toString() {
        return String.valueOf(districtNum);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof District // instanceof handles nulls
                && districtNum == ((District) other).districtNum); // state check
    }
}

