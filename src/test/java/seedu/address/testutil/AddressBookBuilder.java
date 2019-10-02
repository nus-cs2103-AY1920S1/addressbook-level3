package seedu.address.testutil;

import seedu.address.model.TravelPal;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TravelPal ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private TravelPal travelPal;

    public AddressBookBuilder() {
        travelPal = new TravelPal();
    }

    public AddressBookBuilder(TravelPal travelPal) {
        this.travelPal = travelPal;
    }

    /**
     * Adds a new {@code Person} to the {@code TravelPal} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        travelPal.addPerson(person);
        return this;
    }

    public TravelPal build() {
        return travelPal;
    }
}
