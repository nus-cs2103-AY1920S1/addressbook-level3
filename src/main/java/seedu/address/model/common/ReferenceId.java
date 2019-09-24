package seedu.address.model.common;

/**
 * Unmodifiable reference ID for a person.
 */
public interface ReferenceId {

    /**
     * Gets the reference identifier of the person.
     */
    String getReferenceIdentifier();

    /**
     * Checks whether the person is a doctor working as part of the staff.
     */
    boolean isStaffDoctor();

    /**
     * Checks whether the person is a patient.
     */
    boolean isPatient();
}
