package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a MedicalCondition in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidMedicalCondition(String)}
 */
public class MedicalCondition {

    public static final String MESSAGE_CONSTRAINTS = "Medical conditions should be alphanumeric";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code MedicalCondition}.
     *
     * @param medicalCondition A valid medical condition.
     */
    public MedicalCondition(String medicalCondition) {
        requireNonNull(medicalCondition);
        checkArgument(isValidMedicalCondition(medicalCondition), MESSAGE_CONSTRAINTS);
        value = medicalCondition;
    }

    /**
     * Returns true if a given string is a valid medical condition.
     */
    public static boolean isValidMedicalCondition(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicalCondition // instanceof handles nulls
                && value.equals(((MedicalCondition) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
