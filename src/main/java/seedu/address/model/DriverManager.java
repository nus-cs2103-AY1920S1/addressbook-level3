package seedu.address.model;

import java.util.Comparator;
import java.util.List;
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

    /**
     * Sorts driver list accordingly to the comparator provided.
     */
    public static List<Driver> getSortedDriverList(List<Driver> drivers, Comparator<Driver> comparator) {
        drivers.sort(comparator);
        return drivers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DriverManager otherObject = (DriverManager) o;
        return getDriverList().equals(otherObject.getDriverList());
    }
}
