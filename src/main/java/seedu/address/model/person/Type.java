package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's type in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */
public class Type {

    public static final String MESSAGE_CONSTRAINTS = "Types can only take 'patient', 'donor', or 'doctor' as values "
            + "and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public static final String PATIENT = "patient";
    public static final String DOCTOR = "doctor";
    public static final String DONOR = "donor";

    public final String value;

    /**
     * Constructs an {@code Type}.
     *
     * @param type A valid type.
     */
    public Type(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_CONSTRAINTS);
        value = type;
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
        return test.matches(VALIDATION_REGEX)
                && (test.toLowerCase().equals(PATIENT) || test.toLowerCase().equals(DONOR)
                        || test.toLowerCase().equals(DOCTOR));
    }

    /**
     * Checks if the {@code Type} is a patient.
     * @return boolean if this Type is a patient
     */
    public boolean isPatient() {
        return value.equals(PATIENT);
    }

    /**
     * Checks if the {@code Type} is a donor.
     * @return boolean if this Type is a donor
     */
    public boolean isDonor() {
        return value.equals(DONOR);
    }

    /**
     * Checks if the {@code Type} is a doctor.
     * @return boolean if this Type is a doctor
     */
    public boolean isDoctor() {
        return value.equals(DOCTOR);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Type // instanceof handles nulls
            && value.equals(((Type) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
