package seedu.address.model;

import seedu.address.model.person.Driver;
import seedu.address.model.person.Person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the in-memory model of the drivers' data.
 */
public class DriverManager extends ModelManager{
    public DriverManager() {
        super();
    }

    public void addDriver(Driver driver) {
        super.addPerson(driver);
    }

    public void deleteDriver(Driver target) {
        super.deletePerson((Person)target);
    }

    public void setDriver(Driver target, Driver editedDriver) {
        super.setPerson((Person)target, (Person)editedDriver);
    }
}
