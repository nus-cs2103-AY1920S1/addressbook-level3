package seedu.address.model.person.parameters;


/**
 * Represents a Reference ID for Patient.
 * Guarantees: Reference Id is present, validated and immutable.
 */
public class PatientReferenceId extends PersonReferenceId {

    public static final String MESSAGE_CONSTRAINTS =
        "Reference Id for patients should not start with 'DR'";

    /*
     * The reference ID should only contain alphanumeric characters.
     */
    public static final String VALIDATION_REGEX = "DR\\w*";
    /**
     * Constructs a {@code PatientReferenceId}.
     *
     * @param referenceId A valid identifier.
     */
    public PatientReferenceId(String referenceId) {
        super(referenceId);
    }

    /**
     * Returns true if a given string is a valid reference id for patient.
     */
    public static boolean isValidPatientId(String test) {
        return isValidId(test) && !test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean isStaffDoctor() {
        return false;
    }

    @Override
    public boolean isPatient() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof PatientReferenceId // instanceof handles nulls
            && getReferenceIdentifier().equals(((PatientReferenceId) other).getReferenceIdentifier())); // state check
    }
}
