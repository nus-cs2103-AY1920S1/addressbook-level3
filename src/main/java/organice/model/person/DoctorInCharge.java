package organice.model.person;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's doctor in charge in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidDoctorInCharge(String)}
 */
public class DoctorInCharge {

    public static final String MESSAGE_CONSTRAINTS =
            "Doctor in charge's NRIC must start with a letter 'S/T/F/G', 7 numbers afterward "
                    + "and its must end with a letter";
    public static final String MESSAGE_DOCTOR_NOT_FOUND = "We could not identify the doctor that you have specified."
            + " Please check and try again!";
    public static final String VALIDATION_REGEX = "^[STFG]\\d{7}[A-Z]$";

    public final String value;

    /**
     * Constructs a {@code DoctorInCharge}.
     *
     * @param doctorInCharge A valid DoctorInCharge.
     */
    public DoctorInCharge(String doctorInCharge) {
        requireNonNull(doctorInCharge);
        checkArgument(isValidDoctorInCharge(doctorInCharge), MESSAGE_CONSTRAINTS);
        value = doctorInCharge.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid doctor in charge.
     */
    public static boolean isValidDoctorInCharge(String test) {
        return test.toUpperCase().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DoctorInCharge // instanceof handles nulls
            && value.equals(((DoctorInCharge) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
