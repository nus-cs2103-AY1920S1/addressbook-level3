package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Phone;
import seedu.address.model.day.Day;
import seedu.address.model.field.Name;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final AccommodationManager accommodations;
    private final ActivityManager activities;
    private final ContactManager contacts;
    private final Itinerary itinerary;
    private final FilteredList<Accommodation> filteredAccommodations;
    private final FilteredList<Activity> filteredActivities;
    private final FilteredList<Contact> filteredContacts;
    private final FilteredList<Day> filteredItinerary;

    /**
     * Initializes a ModelManager with the given address and userPrefs.
     */
    public ModelManager(ReadOnlyAccommodation accommodations, ReadOnlyActivity activities,
                        ReadOnlyContact contacts, ReadOnlyItinerary itinerary, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(accommodations, activities, contacts, itinerary, userPrefs);

        logger.fine("Initializing with the following: " + accommodations + activities + contacts + itinerary
                + " and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.accommodations = new AccommodationManager(accommodations);
        this.activities = new ActivityManager(activities);
        this.contacts = new ContactManager(contacts);
        this.itinerary = new Itinerary(itinerary);
        filteredAccommodations = new FilteredList<>(this.accommodations.getAccommodationList());
        filteredActivities = new FilteredList<>(this.activities.getActivityList());
        filteredContacts = new FilteredList<>(this.contacts.getContactList());
        filteredItinerary = new FilteredList<>(this.itinerary.getItinerary());
    }

    public ModelManager() {
        this(new AccommodationManager(), new ActivityManager(), new ContactManager(), new Itinerary(), new UserPrefs());
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
    public Path getAccommodationFilePath() {
        return userPrefs.getAccommodationFilePath();
    }

    @Override
    public void setAccommodationFilePath(Path accommodationFilePath) {
        requireNonNull(accommodationFilePath);
        userPrefs.setAccommodationFilePath(accommodationFilePath);
    }

    @Override
    public Path getActivityFilePath() {
        return userPrefs.getActivityFilePath();
    }

    @Override
    public void setActivityFilePath(Path activityFilePath) {
        requireNonNull(activityFilePath);
        userPrefs.setActivityFilePath(activityFilePath);
    }

    @Override
    public Path getContactFilePath() {
        return userPrefs.getContactFilePath();
    }

    @Override
    public void setContactFilePath(Path contactFilePath) {
        requireNonNull(contactFilePath);
        userPrefs.setContactFilePath(contactFilePath);
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

    //=========== AccommodationManager =========================================================================

    @Override
    public void setAccommodations(ReadOnlyAccommodation accommodations) {
        this.accommodations.resetDataAccommodation(accommodations);
    }

    @Override
    public ReadOnlyAccommodation getAccommodations() {
        return accommodations;
    }

    @Override
    public boolean hasAccommodation(Accommodation accommodation) {
        requireNonNull(accommodation);
        return accommodations.hasAccommodation(accommodation);
    }

    @Override
    public void deleteAccommodation(Accommodation target) {
        accommodations.removeAccommodation(target);
    }

    @Override
    public void addAccommodation(Accommodation accommodation) {
        accommodations.addAccommodation(accommodation);
        updateFilteredAccommodationList(PREDICATE_SHOW_ALL_ACCOMMODATIONS);
    }

    @Override
    public void addAccommodationAtIndex(Index index, Accommodation accommodation) {
        accommodations.addAccommodationAtIndex(index, accommodation);
        updateFilteredAccommodationList(PREDICATE_SHOW_ALL_ACCOMMODATIONS);
    }

    @Override
    public void setAccommodation(Accommodation target, Accommodation editedAccommodation) {
        requireAllNonNull(target, editedAccommodation);

        accommodations.setAccommodation(target, editedAccommodation);
    }

    //=========== ActivityManager ============================================================================

    @Override
    public void setActivities(ReadOnlyActivity activities) {
        this.activities.resetDataActivity(activities);
    }

    @Override
    public ReadOnlyActivity getActivities() {
        return activities;
    }

    @Override
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return activities.hasActivity(activity);
    }

    @Override
    public void deleteActivity(Activity target) {
        activities.removeActivity(target);
    }

    @Override
    public void addActivity(Activity activity) {
        activities.addActivity(activity);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    @Override
    public void addActivityAtIndex(Index index, Activity activity) {
        activities.addActivityAtIndex(index, activity);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    @Override
    public void setActivity(Activity target, Activity editedActivity) {
        requireAllNonNull(target, editedActivity);

        activities.setActivity(target, editedActivity);
    }

    //=========== ContactManager =============================================================================

    @Override
    public void setContacts(ReadOnlyContact contacts) {
        this.contacts.resetDataContact(contacts);
    }

    @Override
    public ReadOnlyContact getContacts() {
        return contacts;
    }

    @Override
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return contacts.hasContact(contact);
    }

    @Override
    public boolean hasPhone(Phone phone) {
        requireNonNull(phone);
        return contacts.hasPhone(phone);
    }

    @Override
    public void deleteContact(Contact target) {
        contacts.removeContact(target);
    }

    @Override
    public void addContact(Contact contact) {
        contacts.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
    }

    @Override
    public void addContactAtIndex(Index index, Contact contact) {
        contacts.addContactAtIndex(index, contact);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        contacts.setContact(target, editedContact);
    }

    @Override
    public Optional<Contact> getContactByPhone(Phone toGet) {
        requireNonNull(toGet);
        return contacts.getContactWithPhone(toGet);
    }

    //=========== Itinerary ================================================================================
    @Override
    public Name getName() {
        return this.itinerary.getName();
    }

    @Override
    public void setItineraryName(Name name) {
        this.itinerary.setName(name);
    }

    @Override
    public LocalDate getStartDate() {
        return this.itinerary.getStartDate();
    }

    @Override
    public void setItineraryStartDate(LocalDate date) {
        this.itinerary.setStartDate(date);
    }

    @Override
    public void setItinerary(ReadOnlyItinerary itinerary) {
        this.itinerary.resetDataItinerary(itinerary);
    }

    @Override
    public ReadOnlyItinerary getItinerary() {
        return itinerary;
    }

    @Override
    public void deleteDay(Day target) {
        itinerary.removeDay(target);
    }

    @Override
    public void addDays(int n) {
        itinerary.addDays(n);
    }

    @Override
    public void setDays(List<Day> itinerary) {
        this.itinerary.setDays(itinerary);
    }

    @Override
    public boolean hasDay(Day day) {
        requireNonNull(day);
        return itinerary.hasDay(day);
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

    // FilteredItinerary
    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Day> getFilteredItinerary() {
        return filteredItinerary;
    }

    @Override
    public void updateFilteredItinerary(Predicate<Day> predicate) {
        requireNonNull(predicate);
        filteredItinerary.setPredicate(predicate);
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
        return accommodations.equals(other.accommodations)
                && activities.equals(other.activities)
                && contacts.equals(other.contacts)
                && itinerary.equals(other.itinerary)
                && userPrefs.equals(other.userPrefs)
                && filteredAccommodations.equals(other.filteredAccommodations)
                && filteredActivities.equals(other.filteredActivities)
                && filteredContacts.equals(other.filteredContacts)
                && filteredItinerary.equals(other.filteredItinerary);
    }

}
