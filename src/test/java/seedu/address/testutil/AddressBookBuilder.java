package seedu.address.testutil;

import seedu.address.model.Itinerary;
import seedu.address.model.person.Contact;

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
     * Adds a new {@code Contact} to the {@code Itinerary} that we are building.
     */
    public AddressBookBuilder withPerson(Contact contact) {
        itinerary.addPerson(contact);
        return this;
    }

    public Itinerary build() {
        return itinerary;
    }
}
