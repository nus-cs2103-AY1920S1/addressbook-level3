package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Phone;
import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.day.Day;
import seedu.address.model.field.Name;
import seedu.address.model.itineraryitem.ItineraryItem;
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
    private final HashMap<Contact, List<ItineraryItem>> contactMap;
    private final HashMap<ItineraryItem, Contact> itineraryItemMap;
    private final HashMap<Activity, List<Day>> activityDayMap;
    private final HashMap<Accommodation, List<Day>> accommodationDayMap;

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
        contactMap = new HashMap<>();
        itineraryItemMap = new HashMap<>();
        activityDayMap = new HashMap<>();
        accommodationDayMap = new HashMap<>();
        initMap();
    }

    public ModelManager() {
        this(new AccommodationManager(), new ActivityManager(), new ContactManager(), new Itinerary(), new UserPrefs());
    }

    //=========== Mapping ====================================================================================

    /**
     * Initiates mapping of {@code ItineraryItem} to {@code Contact}, vice versa.
     */
    private void initMap() {
        populateMap(accommodations.getAccommodationList());
        populateMap(activities.getActivityList());
    }

    /**
     * Iterates through the {@code List} of {@code ItineraryItem} and creates a mapping between it's {@code Contact},
     * if it possesses one, to itself.
     */
    private void populateMap(List<? extends ItineraryItem> list) {
        for (ItineraryItem item : list) {
            if (item.getContact().isPresent()) {
                Contact contact = getContactByPhone(item.getContact().get().getPhone()).get();
                if (contactMap.containsKey(contact)) {
                    contactMap.get(contact).add(item);
                } else {
                    contactMap.put(contact, new ArrayList<>((Arrays.asList(item))));
                }
                itineraryItemMap.put(item, contact);
            }
            if (item instanceof Activity) {
                List<Day> days = getDays((Activity) item);
                activityDayMap.put((Activity) item, days);
            }
        }
    }

    /**
     * Creates a mapping between an {@code ItineraryItem} and it's {@code Contact}, if it possesses one
     */
    private void addItineraryItemMapping(ItineraryItem item) {
        if (item.getContact().isPresent()) {
            Contact contact = item.getContact().get();
            if (contactMap.containsKey(contact)) {
                contactMap.get(contact).add(item);
            } else {
                contactMap.put(contact, new ArrayList<>((Arrays.asList(item))));
            }
            itineraryItemMap.put(item, contact);
        }
    }

    /**
     * Updates the mapping when there is a change to an {@code ItineraryItem}. When the {@code Contact} of the
     * {@code ItineraryItem} is changed, the {@code Contact} is also updated.
     */
    private void updateMapping(ItineraryItem oldItem, ItineraryItem newItem) {
        if (newItem.getContact().isPresent()) {
            if (oldItem.getContact().isPresent()) {
                Contact oldContact = itineraryItemMap.remove(oldItem);
                List<ItineraryItem> oldList = contactMap.remove(oldContact);
                Contact newContact = newItem.getContact().get();
                oldList.set(oldList.indexOf(oldItem), newItem);

                setContact(oldContact, newContact);
                contactMap.put(newContact, oldList);
                itineraryItemMap.put(newItem, newContact);
            } else {
                addItineraryItemMapping(newItem);
            }
        }
        if (oldItem instanceof Activity) {
            if (activityDayMap.containsKey((Activity) oldItem)) {
                List<Day> listOfDays = activityDayMap.remove((Activity) oldItem);
                itinerary.getItinerary().forEach(x -> {
                    if (listOfDays.contains(x)) {
                        List<ActivityWithTime> listOfActivityWithTime = x.getListOfActivityWithTime();
                        int indexOfOldItem = listOfActivityWithTime.indexOf(oldItem);
                        ActivityWithTime oldActivityWithTime = listOfActivityWithTime.get(indexOfOldItem);
                        listOfActivityWithTime.set(indexOfOldItem, new ActivityWithTime((Activity) oldItem,
                                oldActivityWithTime.getStartTime(), oldActivityWithTime.getEndTime()));
                    }
                });
                activityDayMap.put((Activity) newItem, listOfDays);
            }
        }
    }

    /**
     * Updates the mapping when there is a change to a {@code Contact}. When the {@code Contact} changes, the
     * {@code ItineraryItem}s sharing the same contact is also updated.
     */
    private void updateMapping(Contact oldContact, Contact newContact) {
        if (contactMap.containsKey(oldContact)) {
            List<ItineraryItem> oldList = contactMap.remove(oldContact);
            List<ItineraryItem> newList = oldList.stream().map(x -> {
                if (x instanceof Activity) {
                    Activity newActivity = new Activity(x.getName(), x.getAddress(), newContact,
                            x.getTags(), ((Activity) x).getDuration(), ((Activity) x).getPriority());
                    activities.setActivity((Activity) x, newActivity);
                    itineraryItemMap.remove(x);
                    itineraryItemMap.put(newActivity, newContact);
                    if (activityDayMap.containsKey(x)) {
                        List<Day> listOfDays = activityDayMap.remove(x);
                        itinerary.getItinerary().forEach(y -> {
                            if (listOfDays.contains(y)) {
                                List<ActivityWithTime> listOfActivityWithTime = y.getListOfActivityWithTime();
                                int indexOfOldItem = listOfActivityWithTime.indexOf(x);
                                ActivityWithTime oldActivityWithTime = listOfActivityWithTime.get(indexOfOldItem);
                                listOfActivityWithTime.set(indexOfOldItem, new ActivityWithTime(newActivity,
                                        oldActivityWithTime.getStartTime(), oldActivityWithTime.getEndTime()));
                            }
                        });
                        activityDayMap.put(newActivity, listOfDays);
                    }
                    return newActivity;
                } else {
                    Accommodation newAccommodation = new Accommodation(x.getName(), x.getAddress(),
                            newContact, x.getTags());
                    accommodations.setAccommodation((Accommodation) x, newAccommodation);
                    itineraryItemMap.remove(x);
                    itineraryItemMap.put(newAccommodation, newContact);
                    return newAccommodation;
                }
            }).collect(Collectors.toList());
            contactMap.put(newContact, newList);
        }
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
    public Optional<Index> getAccommodationIndex(Accommodation accommodation) {
        return accommodations.findAccommodationIndex(accommodation);
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
        addItineraryItemMapping(accommodation);
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
        updateMapping(target, editedAccommodation);
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
    public Optional<Index> getActivityIndex(Activity activity) {
        return activities.findActivityIndex(activity);
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
        addItineraryItemMapping(activity);
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
        updateMapping(target, editedActivity);
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
    public Optional<Index> getContactIndex(Contact contact) {
        return contacts.findContactIndex(contact);
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
        updateMapping(target, editedContact);
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

    public List<Day> getDays(Activity activity) {
        return itinerary.getDays(activity);
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
