package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's bloodtype in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodtype(String)}
 */
public class Bloodtype {


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
        value = bloodtype;
    }

    /**
     * Returns true if a given string is a valid bloodtype.
     */
    public static boolean isValidBloodtype(String test) {
        return (test.toUpperCase().equals("A") || test.toUpperCase().equals("B") || test.toUpperCase().equals("O")
                || test.toUpperCase().equals("AB") || test.toUpperCase().equals("A+") || test.toUpperCase().equals("B+")
                || test.toUpperCase().equals("O+") || test.toUpperCase().equals("AB+"));
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
