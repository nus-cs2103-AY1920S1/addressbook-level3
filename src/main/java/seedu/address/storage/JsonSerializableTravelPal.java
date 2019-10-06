package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTravelPal;
import seedu.address.model.TravelPal;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.exceptions.ClashingTripException;

/**
 * An Immutable TravelPal that is serializable to JSON format.
 */
@JsonRootName(value = "travelpal")
class JsonSerializableTravelPal {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_TRIP = "Trip list contains duplicate trip(s).";
    public static final String MESSAGE_CLASHING_TRIP = "Trip list contains clashing trip";

    private final List<JsonAdaptedTrip> trips = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTravelPal} with the given persons.
     */
    @JsonCreator
    public JsonSerializableTravelPal(@JsonProperty("trips") List<JsonAdaptedTrip> trips) {
        this.trips.addAll(trips);
    }

    /**
     * Converts a given {@code ReadOnlyTravelPal} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTravelPal}.
     */
    public JsonSerializableTravelPal(ReadOnlyTravelPal source) {
        trips.addAll(source.getTripList().stream().map(JsonAdaptedTrip::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code TravelPal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TravelPal toModelType() throws IllegalValueException {
        TravelPal travelPal = new TravelPal();

        for (JsonAdaptedTrip jsonAdaptedTrip : trips) {
            Trip trip = jsonAdaptedTrip.toModelType();

            if (travelPal.hasTrip(trip)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TRIP);
            }

            try {
                travelPal.addTrip(trip);
            } catch (ClashingTripException ex) {
                throw new IllegalValueException(MESSAGE_CLASHING_TRIP);
            }
        }
        return travelPal;
    }

}
