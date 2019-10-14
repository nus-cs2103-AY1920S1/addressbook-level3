package organice.model.person;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents a Person's blood type in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodType(String)}
 */
public class BloodType {

    public static final HashSet<String> BLOOD_TYPES =
            new HashSet<>(Arrays.asList("A", "B", "AB", "O", "A+", "AB+", "B+", "O+"));
    public static final String MESSAGE_CONSTRAINTS =
            "Blood type should only have A, B, O or AB."
                    + "Positive blood types will have a '+' behind, it should not be blank";
    public final String value;

    /**
     * Constructs a {@code BloodType}.
     *
     * @param bloodType A valid blood type.
     */
    public BloodType(String bloodType) {
        requireNonNull(bloodType);
        checkArgument(isValidBloodType(bloodType), MESSAGE_CONSTRAINTS);
        value = bloodType.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid blood type.
     */
    public static boolean isValidBloodType(String test) {
        return BLOOD_TYPES.contains(test.toUpperCase());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodType // instanceof handles nulls
                && value.equals(((BloodType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
