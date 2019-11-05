package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.List;

import seedu.address.model.ReferenceId;
import seedu.address.model.common.UniqueElementList;
import seedu.address.model.exceptions.DuplicateEntryException;
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

    private HashMap<ReferenceId, Person> personHashMap = new HashMap<>();

    @Override
    public boolean contains(Person toCheck) {
        return contains(toCheck.getReferenceId());
    }

    /**
     * Returns true if the list contains an  person whose reference id is equivalent to the given argument.
     */
    public boolean contains(ReferenceId toCheck) {
        requireNonNull(toCheck);
        assert personHashMap.size() == internalList.size();
        return personHashMap.containsKey(toCheck);
    }

    /**
     * Returns a person with the same identity as {@code ReferenceId} who exists in the address book, otherwise null.
     */
    public Person getPerson(ReferenceId id) throws EntryNotFoundException {
        requireNonNull(id);
        Person result = personHashMap.get(id);
        if (result == null) {
            throw new EntryNotFoundException();
        }

        return result;
    }

    @Override
    public boolean containsExact(Person toCheck) {
        requireNonNull(toCheck);
        assert personHashMap.size() == internalList.size();
        return toCheck.equals(personHashMap.get(toCheck.getReferenceId()));
    }

    @Override
    public void add(Person toAdd) {
        super.add(toAdd);
        ReferenceId referenceId = toAdd.getReferenceId();
        referenceId.registerId();
        personHashMap.put(referenceId, toAdd);
        assert personHashMap.size() == internalList.size();
    }

    @Override
    public void set(Person target, Person editedElement) {
        requireAllNonNull(target, editedElement);

        if (target.compareTo(editedElement) != 0 && contains(editedElement)) {
            throw new DuplicateEntryException();
        }

        remove(target);
        add(editedElement);
    }

    @Override
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EntryNotFoundException();
        }
        ReferenceId referenceId = toRemove.getReferenceId();
        referenceId.unregisterId();
        personHashMap.remove(referenceId);
        assert personHashMap.size() == internalList.size();
    }

    @Override
    public void setAll(UniqueElementList<Person> replacement) {
        requireNonNull(replacement);
        super.setAll(replacement);
        personHashMap.clear();
        for (Person person : replacement) {
            personHashMap.put(person.getReferenceId(), person);
        }
    }

    @Override
    public void setAll(List<Person> elements) {
        requireAllNonNull(elements);
        super.setAll(elements);
        personHashMap.clear();
        for (Person person : elements) {
            personHashMap.put(person.getReferenceId(), person);
        }
    }

}
