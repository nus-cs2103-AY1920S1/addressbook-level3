package seedu.address.model.common;

import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * API of the Reference Id Resolver component.
 */
public interface ReferenceIdResolver {

    /**
     * Gets a person whose {@code ReferenceId} matches the given id.
     */
    Person resolve(ReferenceId id) throws PersonNotFoundException;

    /**
     * Checks whether a person whose {@code ReferenceId} matches the given id.
     */
    boolean hasPerson(ReferenceId id);
}
