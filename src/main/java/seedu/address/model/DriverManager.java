package seedu.address.model;

import java.util.Optional;

import seedu.address.model.person.Driver;

/**
 * Represents the in-memory driver model of the address book data.
 */
public class DriverManager extends EntityManager<Driver> {

    public DriverManager() {
        super();
    }

    public boolean hasDriver(Driver driver) {
        return super.hasPerson(driver);
    }

    /**
     * Checks if the driver list has a driver with {@code int customerId}.
     *
     * @param driverId customer unique id.
     */
    public boolean hasDriver(int driverId) {
        return getPersonList()
                .stream()
                .anyMatch(driver -> driver.getId() == driverId);
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

    /**
     * Retrieve driver using its unique driver id.
     *
     * @param driverId driver unique id.
     * @return Driver with the specified unique id.
     */
    public Optional<Driver> getDriver(int driverId) {
        return getPersonList()
                .stream()
                .filter(driver -> driver.getId() == driverId)
                .findFirst();
    }
}
