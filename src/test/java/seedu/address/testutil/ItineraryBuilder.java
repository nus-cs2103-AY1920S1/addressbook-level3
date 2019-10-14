package seedu.address.testutil;

import seedu.address.model.Itinerary;
import seedu.address.model.contact.Contact;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Itinerary ab = new ItineraryBuilder().withPerson("John", "Doe").build();}
 */
public class ItineraryBuilder {

    private Itinerary itinerary;

    public ItineraryBuilder() {
        itinerary = new Itinerary();
    }

    public ItineraryBuilder(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * Adds a new {@code Contact} to the {@code Itinerary} that we are building.
     */
    public ItineraryBuilder withContact(Contact contact) {
        itinerary.addContact(contact);
        return this;
    }

    public Itinerary build() {
        return itinerary;
    }
}
