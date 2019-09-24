package seedu.address.model.common;

/**
 * Represents a Reference ID for Patient.
 * Guarantees: Reference Id is present, validated and immutable.
 */
public class DoctorReferenceId extends PersonReferenceId {

    /**
     * Constructs a {@code DoctorReferenceId}.
     *
     * @param referenceId A valid identifier.
     */
    public DoctorReferenceId(String referenceId) {
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
