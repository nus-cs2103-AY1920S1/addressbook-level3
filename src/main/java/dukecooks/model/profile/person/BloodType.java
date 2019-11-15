package dukecooks.model.profile.person;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;

/**
 * Represents the user's blood type
 */
public class BloodType {
    public static final String MESSAGE_CONSTRAINTS =
            "Blood Type should be alphabetical character (A/B/AB/O) followed by +/-";
    public static final String VALIDATION_REGEX = "(A|B|AB|O)[+-]";

    public final String bloodGroup;

    /**
     * Constructs a {@code BloodType}.
     *
     * @param bloodGroup A valid blood group.
     */
    public BloodType(String bloodGroup) {
        requireNonNull(bloodGroup);
        bloodGroup = bloodGroup.toUpperCase();
        AppUtil.checkArgument(isValidBloodType(bloodGroup), MESSAGE_CONSTRAINTS);
        this.bloodGroup = bloodGroup;
    }

    /**
     * Returns true if a given string is a valid blood group.
     */
    public static boolean isValidBloodType(String test) {
        return test.toUpperCase().matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodType // instanceof handles nulls
                && bloodGroup.equals(((BloodType) other).bloodGroup)); // state check
    }

    @Override
    public int hashCode() {
        return bloodGroup.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return bloodGroup;
    }

}
