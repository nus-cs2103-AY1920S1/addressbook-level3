package seedu.address.model;

import java.nio.file.Path;

import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appstatus.PageStatus;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getTravelPalFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setTravelPalFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code travelPal}.
     */
    void setTravelPal(ReadOnlyTravelPal travelPal);

    /** Returns the TravelPal */
    ReadOnlyTravelPal getTravelPal();

    /**
     * Sets {@link PageStatus} with an edited version.
     *
     * @param editedPageStatus Edited version of {@link PageStatus}
     */
    void setPageStatus(PageStatus editedPageStatus);

    /**
     * Returns {@link PageStatus} in TravelPal.
     * @return {@link PageStatus} of TravelPal
     */
    PageStatus getPageStatus();

    /**
     * Adds a {@link Trip} to TravelPal.
     *
     * @param trip {@link Trip} to be added
     * @throws ClashingTripException Thrown when {@link Trip} with overlapping duration is added.
     */
    void addTrip(Trip trip) throws ClashingTripException;

    /**
     * Replaces an existing {@link Trip} with another.
     *
     * @param target {@link Trip} set for replacement
     * @param replacement Replacement {@link Trip}
     * @throws ClashingTripException Thrown when {@link Trip} with overlapping duration is set
     * @throws TripNotFoundException Thrown when {@code target} cannot be found
     */
    void setTrip(Trip target, Trip replacement) throws ClashingTripException, TripNotFoundException;

    /**
     * Deletes an existing {@link Trip}.
     * @param target {@link Trip} set for deletion
     * @throws TripNotFoundException Thrown when {@code target} cannot be found
     */
    void deleteTrip(Trip target) throws TripNotFoundException;

    /** Returns an unmodifiable view of the filtered trip list */
    FilteredList<Trip> getFilteredTripList();

    /** Returns an unmodifiable view of the filtered currency list */
    FilteredList<CustomisedCurrency> getFilteredCurrencyList();

    /**
     * Deletes the given currency.
     * The currency must exist in the currency list.
     */
    void deleteCurrency(CustomisedCurrency target);

    /**
     * Selects the given currency.
     * The currency must exist in the currency list.
     */
    void selectCurrency(CustomisedCurrency target);

    /**
     * Adds the given currency.
     * {@code currency} must not already exist in the currency list.
     */
    void addCurrency(CustomisedCurrency currency);


    /**
     * Replaces the given currency {@code target} with {@code editedCurrency}.
     * {@code target} must exist in the currency list.
     * The currency identity of {@code editedCurrency} must not be the same as another existing currency
     * in the currency list.
     */
    void setCurrency(CustomisedCurrency target, CustomisedCurrency editedCurrency);
}
