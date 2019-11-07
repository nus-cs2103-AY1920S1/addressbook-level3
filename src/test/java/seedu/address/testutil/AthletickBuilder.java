package seedu.address.testutil;

import seedu.address.model.Athletick;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AthletickBuilder {

    private Athletick athletick;

    public AthletickBuilder() {
        athletick = new Athletick();
    }

    public AthletickBuilder(Athletick addressBook) {
        this.athletick = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AthletickBuilder withPerson(Person person) {
        athletick.addPerson(person);
        return this;
    }

    public Athletick build() {
        return athletick;
    }
}
