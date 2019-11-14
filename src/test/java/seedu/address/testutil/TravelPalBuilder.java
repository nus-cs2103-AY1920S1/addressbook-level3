package seedu.address.testutil;

import seedu.address.model.TravelPal;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.exceptions.ClashingTripException;

/**
 * A utility class to help with building TravelPal objects.
 * Example usage: <br>
 * {@code TravelPal ab = new TravelPalBuilder().withPerson("John", "Doe").build();}
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
     * Adds a trip to the {@code travelPal} instance.
     *
     * @param trip Trip instance to add.
     * @return TravelPalBuilder.
     */
    public TravelPalBuilder withTrip(Trip trip) {
        try {
            travelPal.addTrip(trip);
        } catch (ClashingTripException e) {
            //
        }
        return this;
    }

    public TravelPal build() {
        return travelPal;
    }
}
