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
}
