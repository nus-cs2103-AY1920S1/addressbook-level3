package seedu.address.model.common;

/**
 * Unmodifiable reference ID for a person.
 */
public interface ReferenceId extends Comparable<ReferenceId> {
    /**
     * Checks whether the person is a doctor working as part of the staff.
     */
    boolean isStaffDoctor();

    /**
     * Checks whether the person is a patient.
     */
    boolean isPatient();
}
