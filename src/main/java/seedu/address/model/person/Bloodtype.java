package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents a Person's bloodtype in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodtype(String)}
 */
public class Bloodtype {

    public static final HashSet<String> BLOOD_TYPES =
            new HashSet<>(Arrays.asList("A", "B", "AB", "O", "A+", "AB+", "B+", "O+"));
    public static final String MESSAGE_CONSTRAINTS =
            "Bloodtype should only have A, B, O or AB."
                    + "Positive bloodtypes will have a '+' behind, it should not be blank";
    public final String value;

    /**
     * Constructs a {@code Bloodtype}.
     *
     * @param bloodtype A valid bloodtype.
     */
    public Bloodtype(String bloodtype) {
        requireNonNull(bloodtype);
        checkArgument(isValidBloodtype(bloodtype), MESSAGE_CONSTRAINTS);
        value = bloodtype.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid bloodtype.
     */
    public static boolean isValidBloodtype(String test) {
        return BLOOD_TYPES.contains(test.toUpperCase());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Bloodtype // instanceof handles nulls
                && value.equals(((Bloodtype) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
