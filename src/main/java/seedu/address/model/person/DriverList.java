package seedu.address.model.person;

/**
 * A list of drivers that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class DriverList extends UniquePersonList {

    /**
     * Check the presence of a Driver in the DriverList.
     */
    public boolean containsDriver(Driver toCheck) {
        return contains(toCheck);
    }

    /**
     * Adds a Driver to the DriverList
     *
     * @param toAdd driver to be added to DriverList
     */
    public void addDriver(Driver toAdd) {
        super.add(toAdd);
    }

    /**
     * Removes a Driver from the DriverList
     *
     * @param toRemove Driver to be removed from DriverList
     */
    public void removeDriver(Driver toRemove) {
        super.remove(toRemove);
    }

    /**
     * Replaces an existing Driver from the DriverList with another Driver
     *
     * @param target       Driver to be replaced
     * @param editedDriver new Driver
     */
    public void setDriver(Driver target, Driver editedDriver) {
        super.setPerson((Person) target, (Person) editedDriver);
    }

    /**
     * Replace the entire DriverList with a new DriverList
     *
     * @param replacement new DriverList
     */
    public void setDrivers(DriverList replacement) {
        setPersons(replacement);
    }

}
