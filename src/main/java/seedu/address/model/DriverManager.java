package seedu.address.model;

import seedu.address.model.legacy.AddressBook;
import seedu.address.model.person.Driver;
import seedu.address.model.person.DriverList;

/**
 * Represents the in-memory driver model of the address book data.
 */
public class DriverManager extends AddressBook {

    public DriverManager() {
        persons = new DriverList();
    }

    public boolean hasDriver(Driver driver) {
        return super.hasPerson(driver);
    }

    public void deleteDriver(Driver target) {
        super.removePerson(target);
    }

    public void addDriver(Driver driver) {
        super.addPerson(driver);
    }

    public void setDriver(Driver driver, Driver editedDriver) {
        super.setPerson(driver, editedDriver);
    }
}
