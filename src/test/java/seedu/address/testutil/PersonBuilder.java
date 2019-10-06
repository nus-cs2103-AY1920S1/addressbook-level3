package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";

    private Name name;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public Person build() {
        return new Person(name);
    }

}
