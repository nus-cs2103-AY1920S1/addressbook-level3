package seedu.address.model.common;

import seedu.address.model.exceptions.EntryNotFoundException;
import seedu.address.model.person.Person;

/**
 * API of the Reference Id Resolver component.
 */
public interface ReferenceIdResolver {

    /**
     * Gets a patient whose {@code ReferenceId} matches the given id.
     */
    Person resolvePatient(ReferenceId id) throws EntryNotFoundException;

    /**
     * Gets a staff whose {@code ReferenceId} matches the given id.
     */
    Person resolveStaff(ReferenceId id) throws EntryNotFoundException;

    /**
     * Checks whether a staff whose {@code ReferenceId} matches the given id.
     */
    boolean hasStaff(ReferenceId id);

    /**
     * Checks whether a patient whose {@code ReferenceId} matches the given id.
     */
    boolean hasPatient(ReferenceId id);
}
