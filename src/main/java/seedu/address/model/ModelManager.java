package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.contact.Contact;
import seedu.address.model.day.Day;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Itinerary itinerary;
    private final UserPrefs userPrefs;
    private final FilteredList<Accommodation> filteredAccommodations;
    private final FilteredList<Activity> filteredActivities;
    private final FilteredList<Contact> filteredContacts;

    /**
     * Initializes a ModelManager with the given itinerary and userPrefs.
     */
    public ModelManager(ReadOnlyItinerary addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.itinerary = new Itinerary(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredAccommodations = new FilteredList<>(this.itinerary.getAccommodationList());
        filteredActivities = new FilteredList<>(this.itinerary.getActivityList());
        filteredContacts = new FilteredList<>(this.itinerary.getContactList());
    }

    public ModelManager() {
        this(new Itinerary(), new UserPrefs());
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
    public Path getItineraryFilePath() {
        return userPrefs.getItineraryFilePath();
    }

    @Override
    public void setItineraryFilePath(Path itineraryFilePath) {
        requireNonNull(itineraryFilePath);
        userPrefs.setItineraryFilePath(itineraryFilePath);
    }

    //=========== Itinerary ================================================================================

    @Override
    public void setItinerary(ReadOnlyItinerary itinerary) {

        this.itinerary.resetDataAccommodation(itinerary);
        this.itinerary.resetDataActivity(itinerary);
        this.itinerary.resetDataContact(itinerary);
    }

    @Override
    public ReadOnlyItinerary getItinerary() {
        return itinerary;
    }

    //=========== ACCOMMODATION ================================================================================
    @Override
    public boolean hasAccommodation(Accommodation accommodation) {
        requireNonNull(accommodation);
        return itinerary.hasAccommodation(accommodation);
    }

    @Override
    public void deleteAccommodation(Accommodation target) {
        itinerary.removeAccommodation(target);
    }

    @Override
    public void addAccommodation(Accommodation accommodation) {
        itinerary.addAccommodation(accommodation);
        updateFilteredAccommodationList(PREDICATE_SHOW_ALL_ACCOMMODATIONS);
    }

    @Override
    public void setAccommodation(Accommodation target, Accommodation editedAccommodation) {
        requireAllNonNull(target, editedAccommodation);

        itinerary.setAccommodation(target, editedAccommodation);
    }

    //=========== ACTIVITY ================================================================================
    @Override
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return itinerary.hasActivity(activity);
    }

    @Override
    public void deleteActivity(Activity target) {
        itinerary.removeActivity(target);
    }

    @Override
    public void addActivity(Activity activity) {
        itinerary.addActivity(activity);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    @Override
    public void setActivity(Activity target, Activity editedActivity) {
        requireAllNonNull(target, editedActivity);

        itinerary.setActivity(target, editedActivity);
    }

    //=========== DAY ================================================================================
    @Override
    public void deleteDay(int n) {
        itinerary.removeDay(n);
    }

    @Override
    public void addDays(int n) {
        itinerary.addDays(n);
    }

    @Override
    public void setDays(List<Day> days) {
        itinerary.setDays(days);
    }

    @Override
    public void setDays(int n) {
        itinerary.setDays(n);
    }

    //=========== CONTACT ================================================================================
    @Override
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return itinerary.hasContact(contact);
    }

    @Override
    public void deleteContact(Contact target) {
        itinerary.removeContact(target);
    }

    @Override
    public void addContact(Contact contact) {
        itinerary.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        itinerary.setContact(target, editedContact);
    }

    //=========== Filtered List Accessors =============================================================
    // ACCOMMODATION FilteredList
    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Accommodation> getFilteredAccommodationList() {
        return filteredAccommodations;
    }

    @Override
    public void updateFilteredAccommodationList(Predicate<Accommodation> predicate) {
        requireNonNull(predicate);
        filteredAccommodations.setPredicate(predicate);
    }

    // ACTIVITY FilteredList
    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return filteredActivities;
    }

    @Override
    public void updateFilteredActivityList(Predicate<Activity> predicate) {
        requireNonNull(predicate);
        filteredActivities.setPredicate(predicate);
    }

    // CONTACT FilteredList
    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return filteredContacts;
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
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
        return itinerary.equals(other.itinerary)
                && userPrefs.equals(other.userPrefs)
                && filteredAccommodations.equals(other.filteredAccommodations)
                && filteredActivities.equals(other.filteredActivities)
                && filteredContacts.equals(other.filteredContacts);
    }

}
