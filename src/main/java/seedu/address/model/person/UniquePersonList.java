package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.model.common.ReferenceId;
import seedu.address.model.common.UniqueElementList;
import seedu.address.model.exceptions.EntryNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Person#isSameAs(Person)
 */
public class UniquePersonList extends UniqueElementList<Person> {

    /**
     * Returns true if the list contains an  person whose reference id is equivalent to the given argument.
     */
    public boolean contains(ReferenceId toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(p -> p.isSameAs(toCheck));
    }

    /**
     * Returns a person with the same identity as {@code ReferenceId} who exists in the address book, otherwise null.
     */
    public Person getPerson(ReferenceId id) throws EntryNotFoundException {
        requireNonNull(id);
        Optional<Person> result = internalList.stream().filter(p -> p.isSameAs(id)).findFirst();

        if (result.isEmpty()) {
            throw new EntryNotFoundException();
        }

        return result.get();
    }
}
