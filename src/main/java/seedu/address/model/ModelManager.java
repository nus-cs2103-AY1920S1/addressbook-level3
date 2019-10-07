package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;
import seedu.address.model.appstatus.PageStatus;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TravelPal travelPal;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Trip> filteredTripList;

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
        this.pageStatus = new PageStatus(PageType.TRIP_MANAGER, null, null, null, null);
        filteredPersons = new FilteredList<>(this.travelPal.getPersonList());
        filteredTripList = new FilteredList<>(this.travelPal.getTripList());
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

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return travelPal.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        travelPal.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        travelPal.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        travelPal.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
                && filteredPersons.equals(other.filteredPersons)
                && filteredTripList.equals(other.filteredTripList);
    }
}
