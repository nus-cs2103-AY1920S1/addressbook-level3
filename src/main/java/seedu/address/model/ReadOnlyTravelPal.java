package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.trip.Trip;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTravelPal {

    ObservableList<Trip> getTripList();
    ObservableList<CustomisedCurrency> getCurrencies();

}
