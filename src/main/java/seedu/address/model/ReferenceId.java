//@@author SakuraBlossom
package seedu.address.model;

import seedu.address.model.common.Identical;

/**
 * Unmodifiable reference ID for a person.
 */
public interface ReferenceId extends Identical<ReferenceId> {
    /**
     * Checks whether the person is a doctor working as part of the staff.
     */
    boolean isStaffDoctor();

    /**
     * Checks whether the person is a patient.
     */
    boolean isPatient();

    /**
     * Registers and blocks the current reference id from being re-assigned to a different category (patient or staff).
     */
    void registerId();

    /**
     * Unregisters and frees the current reference id to be re-assigned to a different category (patient or staff).
     */
    void unregisterId();
}
