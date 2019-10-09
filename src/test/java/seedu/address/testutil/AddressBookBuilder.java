package seedu.address.testutil;

import seedu.address.model.Itinerary;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Itinerary ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Itinerary itinerary;

    public AddressBookBuilder() {
        itinerary = new Itinerary();
    }

    public AddressBookBuilder(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * Adds a new {@code Person} to the {@code Itinerary} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        itinerary.addPerson(person);
        return this;
    }

    public Itinerary build() {
        return itinerary;
    }
}
