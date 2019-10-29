package seedu.address.model.person.parameters;

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
        super(referenceId, false);
    }
}
