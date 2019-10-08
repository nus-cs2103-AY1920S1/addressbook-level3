package seedu.address.testutil;

import seedu.address.model.TravelPal;
import seedu.address.model.person.Person;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.exceptions.ClashingTripException;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TravelPal ab = new TravelPalBuilder().withPerson("John", "Doe").build();}
 */
public class TravelPalBuilder {

    private TravelPal travelPal;

    public TravelPalBuilder() {
        travelPal = new TravelPal();
    }

    public TravelPalBuilder(TravelPal travelPal) {
        this.travelPal = travelPal;
    }

    /**
     * Adds a new {@code Person} to the {@code TravelPal} that we are building.
     */
    public TravelPalBuilder withPerson(Person person) {
        travelPal.addPerson(person);
        return this;
    }

    public TravelPalBuilder withTrip (Trip trip) {
        try {
            travelPal.addTrip(trip);
        } catch (ClashingTripException e){

        }
        return this;
    }

    public TravelPal build() {
        return travelPal;
    }
}
