package seedu.address.model;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appstatus.PageStatus;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.currency.exceptions.CurrencyNotFoundException;
import seedu.address.model.currency.exceptions.CurrencyNotRemovableException;
import seedu.address.model.currency.exceptions.DuplicateCurrencyException;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TravelPal travelPal;
    private final UserPrefs userPrefs;
    private final FilteredList<Trip> filteredTripList;
    private final FilteredList<CustomisedCurrency> filteredCurrencyList;

    private PageStatus pageStatus;


    /**
     * Initializes a ModelManager with the given travelPal and userPrefs.
     */
    public ModelManager(ReadOnlyTravelPal addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.travelPal = new TravelPal(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.pageStatus = new PageStatus(PageType.TRIP_MANAGER, null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null,
                null);
        filteredTripList = new FilteredList<>(this.travelPal.getTripList());
        filteredCurrencyList = new FilteredList<>(this.travelPal.getCurrencies());
    }

    public ModelManager() {
        this(new TravelPal(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTravelPalFilePath() {
        return userPrefs.getTravelPalFilePath();
    }

    @Override
    public void setTravelPalFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setTravelPalFilePath(addressBookFilePath);
    }

    //=========== TravelPal ================================================================================

    @Override
    public void setTravelPal(ReadOnlyTravelPal travelPal) {
        this.travelPal.resetData(travelPal);
    }

    @Override
    public ReadOnlyTravelPal getTravelPal() {
        return travelPal;
    }

    //=========== Filtered Trip List Accessors =============================================================

    @Override
    public FilteredList<Trip> getFilteredTripList() {
        return filteredTripList;
    }

    @Override
    public void addTrip(Trip trip) throws ClashingTripException {
        requireNonNull(trip);
        travelPal.addTrip(trip);
    }

    @Override
    public void setTrip(Trip target, Trip replacement) throws ClashingTripException, TripNotFoundException {
        requireAllNonNull(target, replacement);
        travelPal.setTrip(target, replacement);
    }

    @Override
    public void deleteTrip(Trip target) throws TripNotFoundException {
        requireNonNull(target);
        travelPal.deleteTrip(target);
    }

    //=========== Filtered Currency List Accessors =============================================================

    @Override
    public FilteredList<CustomisedCurrency> getFilteredCurrencyList() {
        return filteredCurrencyList;
    }

    @Override
    public void addCurrency(CustomisedCurrency currency) throws DuplicateCurrencyException {
        requireNonNull(currency);
        travelPal.addCurrency(currency);
    }

    @Override
    public void setCurrency(CustomisedCurrency target, CustomisedCurrency replacement)
            throws CurrencyNotFoundException {
        requireAllNonNull(target, replacement);
        travelPal.setCurrency(target, replacement);
    }

    @Override
    public void deleteCurrency(CustomisedCurrency target) throws CurrencyNotFoundException,
            CurrencyNotRemovableException {
        requireNonNull(target);
        travelPal.deleteCurrency(target);
    }

    @Override
    public void selectCurrency(CustomisedCurrency target) throws CurrencyNotFoundException {
        requireNonNull(target);
        travelPal.selectCurrency(target);
    }
    //=========== PageStatus List Accessors =============================================================

    @Override
    public void setPageStatus(PageStatus pageStatus) {
        requireNonNull(pageStatus);
        this.pageStatus = pageStatus;
    }

    @Override
    public PageStatus getPageStatus() {
        return pageStatus;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return travelPal.equals(other.travelPal)
                && userPrefs.equals(other.userPrefs)
                && filteredTripList.equals(other.filteredTripList)
                && pageStatus.equals(other.getPageStatus());
    }
}
