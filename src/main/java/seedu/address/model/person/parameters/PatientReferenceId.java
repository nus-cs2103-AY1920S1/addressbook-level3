package seedu.address.model.person.parameters;


/**
 * Represents a Reference ID for Patient.
 * Guarantees: Reference Id is present, validated and immutable.
 */
public class PatientReferenceId extends PersonReferenceId {

    public static final String MESSAGE_CONSTRAINTS = StaffReferenceId.MESSAGE_CONSTRAINTS_NONSTAFF;

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
        return isValidId(test) && !test.toUpperCase().contains("STAFF");
    }

    @Override
    public boolean isStaffDoctor() {
        return false;
    }

    @Override
    public boolean isPatient() {
        return true;
    }
}
