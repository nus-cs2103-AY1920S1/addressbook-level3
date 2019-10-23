package seedu.address.model.common;

import seedu.address.model.exceptions.EntryNotFoundException;
import seedu.address.model.person.Person;

/**
 * API of the Reference Id Resolver component.
 */
public interface ReferenceIdResolver {

    /**
     * Gets a person whose {@code ReferenceId} matches the given id.
     */
    Person resolve(ReferenceId id) throws EntryNotFoundException;

    /**
     * Checks whether a person whose {@code ReferenceId} matches the given id.
     */
    boolean hasPerson(ReferenceId id);
}
