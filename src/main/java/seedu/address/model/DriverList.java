package seedu.address.model;

import seedu.address.model.person.Driver;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of drivers that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class DriverList extends UniquePersonList {

    public boolean containsDriver(Driver toCheck) {
        return contains(toCheck);
    }

    public void addDriver(Driver toAdd) {
        super.add(toAdd);
    }

    public void removeDriver(Driver toRemove) {
        super.remove(toRemove);
    }

    public void setDriver(Driver target, Driver editedDriver) {
        super.setPerson((Person)target, (Person)editedDriver);
    }

    public void setDrivers(DriverList replacement) {
        setPersons(replacement);
    }

}
