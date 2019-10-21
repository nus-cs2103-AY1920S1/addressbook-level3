package seedu.address.model.bio;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a User's medical condition in the user's biography data.
 * Guarantees: immutable; is valid as declared in {@link #isValidMedicalCondition(String)}
 */
public class MedicalCondition implements ListableField {

    public static final String MESSAGE_CONSTRAINTS =
            "Medical conditions can take any values, and it should not be blank.";

    /*
     * The first character of the medicalCondition must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\"^$|[^\\\\s].*";

    public final String medicalCondition;

    /**
     * Constructs a {@code MedicalCondition}.
     *
     * @param medicalCondition A valid medicalCondition.
     */
    public MedicalCondition(String medicalCondition) {
        requireNonNull(medicalCondition);
        checkArgument(isValidMedicalCondition(medicalCondition), MESSAGE_CONSTRAINTS);
        this.medicalCondition = medicalCondition;
    }

    /**
     * Returns true if a given string is a valid medicalCondition.
     */
    public static boolean isValidMedicalCondition(String test) {
        requireNonNull(test);
        return !test.isEmpty() && test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicalCondition // instanceof handles nulls
                && medicalCondition.equals(((MedicalCondition) other).medicalCondition)); // state check
    }

    @Override
    public int hashCode() {
        return medicalCondition.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return medicalCondition;
    }

}
