package seedu.address.model;

import seedu.address.model.person.Driver;

/**
 * Represents the in-memory driver model of the address book data.
 */
public class DriverManager extends ModelManager {

    public DriverManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super(addressBook, userPrefs);
    }

    public DriverManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public boolean hasDriver(Driver driver) {
        return super.hasPerson(driver);
    }

    public void deleteDriver(Driver target) {
        super.deletePerson(target);
    }

    public void addDriver(Driver driver) {
        super.addPerson(driver);
    }

    public void setDriver(Driver driver, Driver editedDriver) {
        super.setPerson(driver, editedDriver);
    }
}
