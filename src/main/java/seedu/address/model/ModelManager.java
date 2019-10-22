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
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Phone;
import seedu.address.model.day.Day;
import seedu.address.model.day.Itinerary;
import seedu.address.model.field.Name;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Planner planner;
    private final UserPrefs userPrefs;
    private final FilteredList<Accommodation> filteredAccommodations;
    private final FilteredList<Activity> filteredActivities;
    private final FilteredList<Contact> filteredContacts;
    private final FilteredList<Day> filteredDays;

    /**
     * Initializes a ModelManager with the given planner and userPrefs.
     */
    public ModelManager(ReadOnlyPlanner addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.planner = new Planner(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredAccommodations = new FilteredList<>(this.planner.getAccommodationList());
        filteredActivities = new FilteredList<>(this.planner.getActivityList());
        filteredContacts = new FilteredList<>(this.planner.getContactList());
        filteredDays = new FilteredList<>(this.planner.getDayList());
    }

    public ModelManager() {
        this(new Planner(), new UserPrefs());
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
    public Path getPlannerFilePath() {
        return userPrefs.getPlannerFilePath();
    }

    @Override
    public void setPlannerFilePath(Path plannerFilePath) {
        requireNonNull(plannerFilePath);
        userPrefs.setPlannerFilePath(plannerFilePath);
    }

    //=========== Planner ================================================================================

    @Override
    public void setPlanner(ReadOnlyPlanner planner) {
        this.planner.setName(planner.getName());
        this.planner.setStartDate(planner.getStartDate());
        this.planner.resetDataAccommodation(planner);
        this.planner.resetDataActivity(planner);
        this.planner.resetDataContact(planner);
        this.planner.resetDataDay(planner);
    }

    @Override
    public ReadOnlyPlanner getPlanner() {
        return planner;
    }

    //=========== ACCOMMODATION ================================================================================
    @Override
    public boolean hasAccommodation(Accommodation accommodation) {
        requireNonNull(accommodation);
        return planner.hasAccommodation(accommodation);
    }

    @Override
    public void deleteAccommodation(Accommodation target) {
        planner.removeAccommodation(target);
    }

    @Override
    public void addAccommodation(Accommodation accommodation) {
        planner.addAccommodation(accommodation);
        updateFilteredAccommodationList(PREDICATE_SHOW_ALL_ACCOMMODATIONS);
    }

    @Override
    public void setAccommodation(Accommodation target, Accommodation editedAccommodation) {
        requireAllNonNull(target, editedAccommodation);

        planner.setAccommodation(target, editedAccommodation);
    }

    //=========== ACTIVITY ================================================================================
    @Override
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return planner.hasActivity(activity);
    }

    @Override
    public void deleteActivity(Activity target) {
        planner.removeActivity(target);
    }

    @Override
    public void addActivity(Activity activity) {
        planner.addActivity(activity);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    @Override
    public void setActivity(Activity target, Activity editedActivity) {
        requireAllNonNull(target, editedActivity);

        planner.setActivity(target, editedActivity);
    }

    //=========== DAY ================================================================================
    @Override
    public void setItineraryName(Name name) {
        this.planner.setName(name);
    }

    @Override
    public void setItineraryStartDate(LocalDate sd) {
        this.planner.setStartDate(sd);
    }

    @Override
    public void deleteDay(Day target) {
        planner.removeDay(target);
    }

    @Override
    public void addDays(int n) {
        planner.addDays(n);
    }

    @Override
    public void setDays(List<Day> days) {
        planner.setDays(days);
    }

    @Override
    public void setDays(Itinerary itinerary) {
        planner.setDays(itinerary);
    }

    @Override
    public boolean hasDay(Day day) {
        requireNonNull(day);
        return planner.hasDay(day);
    }
    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Day> getFilteredDayList() {
        return filteredDays;
    }

    @Override
    public void updateFilteredDayList(Predicate<Day> predicate) {
        requireNonNull(predicate);
        filteredDays.setPredicate(predicate);
    }

    //=========== CONTACT ================================================================================
    @Override
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return planner.hasContact(contact);
    }

    @Override
    public boolean hasPhone(Phone phone) {
        requireNonNull(phone);
        return planner.hasPhone(phone);
    }

    @Override
    public void deleteContact(Contact target) {
        planner.removeContact(target);
    }

    @Override
    public void addContact(Contact contact) {
        planner.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        planner.setContact(target, editedContact);
    }

    @Override
    public Optional<Contact> getContactByPhone(Phone toGet) {
        requireNonNull(toGet);
        return planner.getContactWithPhone(toGet);
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
        return planner.equals(other.planner)
                && userPrefs.equals(other.userPrefs)
                && filteredAccommodations.equals(other.filteredAccommodations)
                && filteredActivities.equals(other.filteredActivities)
                && filteredContacts.equals(other.filteredContacts);
    }

}
