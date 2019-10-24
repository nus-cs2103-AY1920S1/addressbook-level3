package seedu.address.model;

import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.model.person.Driver;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Manages the customer list.
 * It contains the minimal set of list operations.
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
    public Optional<Driver> getOptionalDriver(int driverId) {
        return getPersonList()
                .stream()
                .filter(driver -> driver.getId() == driverId)
                .findFirst();
    }

    /**
     * Returns an unmodifiable view of the driver list.
     * This list will not contain any duplicate drivers.
     *
     * @return Driver list without duplicate drivers.
     */
    public ObservableList<Driver> getDriverList() {
        return super.getPersonList();
    }

    public Driver getDriver(int driverId) {
        return getPersonList()
                .stream()
                .filter(driver -> driver.getId() == driverId)
                .findFirst()
                .orElseThrow(PersonNotFoundException::new);
    }
}
