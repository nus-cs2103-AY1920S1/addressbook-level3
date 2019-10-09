package seedu.address.model;

import seedu.address.model.person.Driver;

public class DriverManager extends ModelManager{
    private Driver driver;

    public DriverManager(Driver driver) {
        super();
        this.driver = driver;
    }
}
