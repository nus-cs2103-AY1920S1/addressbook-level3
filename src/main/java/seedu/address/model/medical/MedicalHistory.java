package seedu.address.model.medical;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a MedicalHistory in Duke Cooks.
 * Guarantees: immutable; name is valid as declared in {@link #isValidMedicalHistoryName(String)}
 */
public class MedicalHistory {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String medicalHistoryName;

    /**
     * Constructs a {@code MedicalHistory}.
     *
     * @param medicalHistoryName A valid tag name.
     */
    public MedicalHistory(String medicalHistoryName) {
        requireNonNull(medicalHistoryName);
        checkArgument(isValidMedicalHistoryName(medicalHistoryName), MESSAGE_CONSTRAINTS);
        this.medicalHistoryName = medicalHistoryName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidMedicalHistoryName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicalHistory // instanceof handles nulls
                && medicalHistoryName.equals(((MedicalHistory) other).medicalHistoryName)); // state check
    }

    @Override
    public int hashCode() {
        return medicalHistoryName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + medicalHistoryName + ']';
    }

}
