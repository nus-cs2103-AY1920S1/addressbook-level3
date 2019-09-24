package seedu.address.model.common;


/**
 * Represents a Reference ID for Patient.
 * Guarantees: Reference Id is present, validated and immutable.
 */
public class PatientReferenceId extends PersonReferenceId {

    /**
     * Constructs a {@code PatientReferenceId}.
     *
     * @param referenceId A valid identifier.
     */
    public PatientReferenceId(String referenceId) {
        super(referenceId);
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
