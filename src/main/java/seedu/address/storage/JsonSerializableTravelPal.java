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
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.currency.Rate;
import seedu.address.model.currency.Symbol;
import seedu.address.model.itinerary.Name;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.exceptions.ClashingTripException;

/**
 * An Immutable TravelPal that is serializable to JSON format.
 */
@JsonRootName(value = "travelpal")
class JsonSerializableTravelPal {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_TRIP = "Trip list contains duplicate trip(s).";
    public static final String MESSAGE_DUPLICATE_CURRENCY = "Trip list contains duplicate currency(s).";
    public static final String MESSAGE_CLASHING_TRIP = "Trip list contains clashing trip";

    private final List<JsonAdaptedTrip> trips = new ArrayList<>();
    private final List<JsonAdaptedCurrency> currencies = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTravelPal} with the given persons.
     */
    @JsonCreator
    public JsonSerializableTravelPal(@JsonProperty("trips") List<JsonAdaptedTrip> trips,
                                     @JsonProperty("currencies") List<JsonAdaptedCurrency> currencies) {
        this.trips.addAll(trips);
        this.currencies.addAll(currencies);
    }

    /**
     * Converts a given {@code ReadOnlyTravelPal} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTravelPal}.
     */
    JsonSerializableTravelPal(ReadOnlyTravelPal source) {
        trips.addAll(source.getTripList().stream().map(JsonAdaptedTrip::new).collect(Collectors.toList()));
        currencies.addAll(source.getCurrencies().stream().map(JsonAdaptedCurrency::new).collect(Collectors.toList()));
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

        for (JsonAdaptedCurrency jsonAdaptedCurrency : currencies) {
            CustomisedCurrency currency = jsonAdaptedCurrency.toModelType();

            if (currency.isSameCustomisedCurrency(
                    new CustomisedCurrency(new Name("SGD"), new Symbol("1"), new Rate("1.00")))) {
                continue;
            }
            if (travelPal.hasCurrency(currency)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CURRENCY);
            }

            travelPal.addCurrency(currency);
        }

        return travelPal;
    }

}
