package seedu.planner.model;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.planner.commons.core.GuiSettings;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.index.Index;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.model.day.Day;
import seedu.planner.model.field.Name;

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
    private final HashMap<Contact, List<Activity>> contactActivityMap;
    private final HashMap<Contact, List<Accommodation>> contactAccommodationMap;
    private final HashMap<Activity, Contact> activityContactMap;
    private final HashMap<Accommodation, Contact> accommodationContactMap;
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
        contactActivityMap = new HashMap<>();
        contactAccommodationMap = new HashMap<>();
        activityContactMap = new HashMap<>();
        accommodationContactMap = new HashMap<>();
        activityDayMap = new HashMap<>();
        accommodationDayMap = new HashMap<>();
        initMap();
    }

    public ModelManager() {
        this(new AccommodationManager(), new ActivityManager(), new ContactManager(), new Itinerary(), new UserPrefs());
    }

    //=========== Mapping ====================================================================================

    /**
     * Initiates mapping of {@code Activity} and {@code Accommodation} to {@code Contact}, vice versa.
     */
    private void initMap() {
        populateActivityMap(this.activities.getActivityList());
        populateAccommodationMap(this.accommodations.getAccommodationList());
        populateDayMap(this.itinerary.getItinerary());
    }

    /**
     * Iterates through the {@code List} of {@code Activity} and creates a mapping between it's {@code Contact},
     * if it possesses one, to itself.
     */
    private void populateActivityMap(List<Activity> list) {
        for (Activity act : list) {
            if (act.getContact().isPresent()) {
                Contact contact = getContactByPhone(act.getContact().get().getPhone()).get();
                if (contactActivityMap.containsKey(contact)) {
                    contactActivityMap.get(contact).add(act);
                } else {
                    contactActivityMap.put(contact, new ArrayList<>(Arrays.asList(act)));
                }
                activityContactMap.put(act, contact);
            }
            List<Day> days = getDays(act);
            activityDayMap.put(act, days);
        }
    }

    /**
     * Iterates through the {@code List} of {@code Accommodation} and creates a mapping between it's {@code Contact},
     * if it possesses one, to itself.
     */
    private void populateAccommodationMap(List<Accommodation> list) {
        for (Accommodation acc : list) {
            if (acc.getContact().isPresent()) {
                Contact contact = getContactByPhone(acc.getContact().get().getPhone()).get();
                if (contactAccommodationMap.containsKey(contact)) {
                    contactAccommodationMap.get(contact).add(acc);
                } else {
                    contactAccommodationMap.put(contact, new ArrayList<>(Arrays.asList(acc)));
                }
                accommodationContactMap.put(acc, contact);
            }
        }
    }

    /**
     * Iterates through the {@code Itinerary} and creates a mapping between an {@code Activity} and the {@code Day}
     * containing it.
     */
    private void populateDayMap(List<Day> days) {
        for (Day day : days) {
            List<ActivityWithTime> activities = day.getListOfActivityWithTime();
            for (ActivityWithTime act : activities) {
                if (activityDayMap.containsKey(act.getActivity())) {
                    activityDayMap.get(act.getActivity()).add(day);
                } else {
                    activityDayMap.put(act.getActivity(), new ArrayList<>(Arrays.asList(day)));
                }
            }
        }
    }

    /**
     * Creates a mapping between an {@code Activity} and it's {@code Contact}, if it possesses one.
     */
    private void addActivityMapping(Activity act) {
        if (act.getContact().isPresent()) {
            Contact contact = act.getContact().get();
            if (contactActivityMap.containsKey(contact)) {
                contactActivityMap.get(contact).add(act);
            } else {
                contactActivityMap.put(contact, new ArrayList<>(Arrays.asList(act)));
            }
            activityContactMap.put(act, contact);
        }
    }

    /**
     * Creates a mapping between an {@code Activity} and the {@code Day} containing it.
     */
    private void addDayMapping(Activity act, Day day) {
        if (activityDayMap.containsKey(act)) {
            activityDayMap.get(act).add(day);
        } else {
            activityDayMap.put(act, new ArrayList<>(Arrays.asList(day)));
        }
    }

    /**
     * Removes the mapping of an {@code Activity} in all the relevant {@code Contact} related {@code HashMap}.
     */
    private void removeActivityMapping(Activity act) {
        if (activityContactMap.containsKey(act)) {
            Contact contact = activityContactMap.remove(act);
            contactActivityMap.remove(contact);
        }
    }

    /**
     * Removes the mapping of an {@code Activity} in the {@code Day} related {@code HashMap} when an activity is
     * deleted.
     */
    private void removeDayMapping(Activity act) {
        if (activityDayMap.containsKey(act)) {
            List<Day> listOfDays = activityDayMap.remove(act);
            for (Day day : listOfDays) {
                day.removeActivity(act);
            }
        }
    }

    /**
     * Removes the mapping of an {@code Activity} in the {@code Day} related {@code HashMap} when an activity is
     * undscheduled.
     */
    private void removeDayMapping(Activity act, Day day) {
        if (activityDayMap.containsKey(act)) {
            activityDayMap.get(act).remove(day);
        }
    }

    /**
     * Creates a mapping between an {@code Accommodation} and it's {@code Contact}, if it possesses one.
     */
    private void addAccommodationMapping(Accommodation acc) {
        if (acc.getContact().isPresent()) {
            Contact contact = acc.getContact().get();
            if (contactAccommodationMap.containsKey(contact)) {
                contactAccommodationMap.get(contact).add(acc);
            } else {
                contactAccommodationMap.put(contact, new ArrayList<>((Arrays.asList(acc))));
            }
            accommodationContactMap.put(acc, contact);
        }
    }

    /**
     * Removes the mapping of a {@code Contact} and it's related {@code Activity} and {@code Accommodation}.
     */
    private void removeContactMapping(Contact contact) {
        if (contactActivityMap.containsKey(contact)) {
            List<Activity> activities = contactActivityMap.remove(contact);
            for (Activity act : activities) {
                if (activityContactMap.containsKey(act)) {
                    activityContactMap.remove(act);
                }
                Activity newAct = new Activity(act.getName(), act.getAddress(), null,
                        act.getCost().isPresent() ? act.getCost().get() : null, act.getTags(), act.getDuration(),
                        act.getPriority());
                this.activities.setActivity(act, newAct);
                updateDay(act, newAct);
            }
        }
        if (contactAccommodationMap.containsKey(contact)) {
            List<Accommodation> accommodations = contactAccommodationMap.remove(contact);
            for (Accommodation acc : accommodations) {
                if (accommodationContactMap.containsKey(acc)) {
                    accommodationContactMap.remove(acc);
                }
                Accommodation newAcc = new Accommodation(acc.getName(), acc.getAddress(), null, acc.getTags());
                this.accommodations.setAccommodation(acc, newAcc);
            }
        }
    }

    /**
     * Updates the mapping when there is a change to an {@code Activity}.  When the {@code Activity} is changed,
     * the {@code Day} containing it is updated.
     */
    private void updateDay(Activity oldAct, Activity newAct) {
        List<Day> listOfDays = activityDayMap.get(oldAct);
        List<Day> newListOfDays = new ArrayList<>();
        itinerary.getItinerary().forEach(day -> {
            if (listOfDays.contains(day)) {
                List<ActivityWithTime> listOfActivityWithTime = day.getListOfActivityWithTime();
                List<Integer> indexOfOldActs = new LinkedList<>();
                for (int i = 0; i < listOfActivityWithTime.size(); i++) {
                    if (listOfActivityWithTime.get(i).getActivity().equals(oldAct)) {
                        indexOfOldActs.add(i);
                    }
                }
                for (Integer i : indexOfOldActs) {
                    ActivityWithTime oldActivityWithTime = listOfActivityWithTime.get(i);
                    listOfActivityWithTime.set(i, new ActivityWithTime(newAct,
                            oldActivityWithTime.getStartTime()));
                }
                Day newDay = new Day(listOfActivityWithTime);
                setDay(day, newDay);
                newListOfDays.add(newDay);
            }
        });
        activityDayMap.remove(oldAct);
        activityDayMap.put(newAct, newListOfDays);
    }

    /**
     * Updates the mapping when there is a change to an {@code Activity}. When the {@code Contact} of the
     * {@code Activity} is changed, the {@code Contact} is also updated.
     */
    private void updateMapping(Activity oldAct, Activity newAct) {
        if (oldAct.getContact().isPresent()) {
            Contact oldContact = activityContactMap.remove(oldAct);
            List<Activity> oldList = contactActivityMap.remove(oldContact);
            oldList.set(oldList.indexOf(oldAct), newAct);
            if (newAct.getContact().isPresent()) {
                Contact newContact = newAct.getContact().get();
                setContact(oldContact, newContact);
                contactActivityMap.put(newContact, oldList);
                activityContactMap.put(newAct, newContact);
            } else {
                contactActivityMap.put(oldContact, oldList);
                activityContactMap.put(newAct, oldContact);
            }
        } else if (newAct.getContact().isPresent()) {
            addContact(newAct.getContact().get());
            addActivityMapping(newAct);
        }
        if (activityDayMap.containsKey(oldAct)) {
            updateDay(oldAct, newAct);
        }
    }

    /**
     * Updates the mapping when there is a change to an {@code Activity}. When the {@code Contact} of the
     * {@code Activity} is changed, the {@code Contact} is also updated.
     */
    private void updateMapping(Accommodation oldAcc, Accommodation newAcc) {
        if (oldAcc.getContact().isPresent()) {
            Contact oldContact = accommodationContactMap.remove(oldAcc);
            List<Accommodation> oldList = contactAccommodationMap.remove(oldContact);
            oldList.set(oldList.indexOf(oldAcc), newAcc);
            if (newAcc.getContact().isPresent()) {
                Contact newContact = newAcc.getContact().get();
                setContact(oldContact, newContact);
                contactAccommodationMap.put(newContact, oldList);
                accommodationContactMap.put(newAcc, newContact);
            } else {
                accommodationContactMap.put(newAcc, oldContact);
                contactAccommodationMap.put(oldContact, oldList);
            }
        } else if (newAcc.getContact().isPresent()) {
            addContact(newAcc.getContact().get());
            addAccommodationMapping(newAcc);
        }
    }

    /**
     * Updates the mapping when there is a change to a {@code Contact}. When the {@code Contact} changes, the
     * {@code Activity} and {@code Accommodation} sharing the same contact is also updated.
     */
    private void updateMapping(Contact oldContact, Contact newContact) {
        if (contactActivityMap.containsKey(oldContact)) {
            List<Activity> oldList = contactActivityMap.remove(oldContact);
            List<Activity> newList = oldList.stream().map(x -> {
                Activity newActivity = new Activity(x.getName(), x.getAddress(), newContact, x.getCost().orElse(null),
                        x.getTags(), x.getDuration(), x.getPriority());
                activities.setActivity(x, newActivity);
                activityContactMap.remove(x);
                activityContactMap.put(newActivity, newContact);
                if (activityDayMap.containsKey(x)) {
                    List<Day> listOfDays = activityDayMap.remove(x);
                    itinerary.getItinerary().forEach(y -> {
                        if (listOfDays.contains(y)) {
                            List<ActivityWithTime> listOfActivityWithTime = y.getListOfActivityWithTime();
                            int indexOfOldItem = listOfActivityWithTime.indexOf(x);
                            ActivityWithTime oldActivityWithTime = listOfActivityWithTime.get(indexOfOldItem);
                            listOfActivityWithTime.set(
                                    indexOfOldItem,
                                    new ActivityWithTime(
                                        newActivity,
                                        oldActivityWithTime.getStartTime()
                                    )
                            );
                        }
                    });
                    activityDayMap.put(newActivity, listOfDays);
                }
                return newActivity;
            }).collect(Collectors.toList());
            contactActivityMap.put(newContact, newList);
        }
        if (contactAccommodationMap.containsKey(oldContact)) {
            List<Accommodation> oldList = contactAccommodationMap.remove(oldContact);
            List<Accommodation> newList = oldList.stream().map(x -> {
                Accommodation newAccommodation = new Accommodation(x.getName(), x.getAddress(),
                        newContact, x.getTags());
                accommodations.setAccommodation(x, newAccommodation);
                accommodationContactMap.remove(x);
                accommodationContactMap.put(newAccommodation, newContact);
                return newAccommodation;
            }).collect(Collectors.toList());
            contactAccommodationMap.put(newContact, newList);
        }
    }

    /**
     * Updates the mapping when there is a change in a {@code Day}.
     */
    private void updateMapping(Day oldDay, Day newDay) {
        List<ActivityWithTime> oldList = oldDay.getListOfActivityWithTime();
        for (ActivityWithTime act : oldList) {
            activityDayMap.get(act.getActivity()).remove(oldDay);
        }
        List<ActivityWithTime> newList = newDay.getListOfActivityWithTime();
        for (ActivityWithTime act : newList) {
            if (activityDayMap.containsKey(act.getActivity())) {
                activityDayMap.get(act.getActivity()).add(newDay);
            } else {
                activityDayMap.put(act.getActivity(), new ArrayList<>(Arrays.asList(newDay)));
            }
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
        addAccommodationMapping(accommodation);
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
        removeActivityMapping(target);
        removeDayMapping(target);
        activities.removeActivity(target);
    }

    @Override
    public void addActivity(Activity activity) {
        activities.addActivity(activity);
        addActivityMapping(activity);
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
        removeContactMapping(target);
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
    public void deleteDays(int n) {
        itinerary.deleteDays(n);
    }

    @Override
    public void addDays(int n) {
        itinerary.addDays(n);
    }

    @Override
    public void addDayAtIndex(Index index, Day day) {
        itinerary.addDayAtIndex(index, day);
        updateFilteredItinerary(PREDICATE_SHOW_ALL_DAYS);
    }

    @Override
    public void setDays(List<Day> itinerary) {
        this.itinerary.setDays(itinerary);
    }

    public void setDay(Day oldDay, Day newDay) {
        updateMapping(oldDay, newDay);
        this.itinerary.setDay(oldDay, newDay);
    }

    @Override
    public boolean hasDay(Day day) {
        requireNonNull(day);
        return itinerary.hasDay(day);
    }

    @Override
    public void scheduleActivity(Day day, ActivityWithTime toAdd) {
        addDayMapping(toAdd.getActivity(), day);
        day.addActivityWithTime(toAdd);
    }

    @Override
    public void unscheduleActivity(Day day, Index toRemove) {
        Activity activity = day.getListOfActivityWithTime().get(toRemove.getZeroBased()).getActivity();
        removeDayMapping(activity, day);
        day.removeActivityWithTime(toRemove);
    }

    public List<Day> getDays(Activity activity) {
        return itinerary.getDays(activity);
    }

    @Override
    public int getNumberOfDays() {
        return itinerary.getNumberOfDays();
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
