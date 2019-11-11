package seedu.planner.model;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

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
import seedu.planner.model.day.exceptions.EndOfTimeException;
import seedu.planner.model.field.Address;
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
    private final HashMap<Contact, Activity> contactActivityMap;
    private final HashMap<Contact, Accommodation> contactAccommodationMap;
    private final HashMap<Activity, List<Day>> activityDayMap;

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
        activityDayMap = new HashMap<>();
        initMap();
    }

    public ModelManager() {
        this(new AccommodationManager(), new ActivityManager(), new ContactManager(), new Itinerary(), new UserPrefs());
    }

    //=========== Mapping ====================================================================================

    //@@author KxxMxxx
    /**
     * Initiates mapping of {@code Activity} and {@code Accommodation} to {@code Contact}, vice versa.
     */
    private void initMap() {
        populateActivityMap(this.activities.getActivityList());
        populateAccommodationMap(this.accommodations.getAccommodationList());
        populateDayMap(this.itinerary.getItinerary());
    }

    //@@author KxxMxxx
    /**
     * Iterates through the {@code List} of {@code Activity} and creates a mapping between it's {@code Contact},
     * if it possesses one, to itself.
     */
    private void populateActivityMap(List<Activity> list) {
        for (Activity act : list) {
            if (act.getContact().isPresent() && hasContact(act.getContact().get())) {
                Contact contact = getContact(act.getContact().get()).get();
                contactActivityMap.put(contact, act);
            } else if (act.getContact().isPresent() && !hasContact(act.getContact().get())) {
                Contact contact = act.getContact().get();
                addContact(contact);
                contactActivityMap.put(contact, act);
            }
        }
    }

    //@@author KxxMxxx
    /**
     * Iterates through the {@code List} of {@code Accommodation} and creates a mapping between it's {@code Contact},
     * if it possesses one, to itself.
     */
    private void populateAccommodationMap(List<Accommodation> list) {
        for (Accommodation acc : list) {
            if (acc.getContact().isPresent() && hasContact(acc.getContact().get())) {
                Contact contact = getContact(acc.getContact().get()).get();
                contactAccommodationMap.put(contact, acc);
            } else if (acc.getContact().isPresent() && !hasContact(acc.getContact().get())) {
                Contact contact = acc.getContact().get();
                addContact(contact);
                contactAccommodationMap.put(contact, acc);
            }
        }
    }

    //@@author KxxMxxx
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

    //@@author KxxMxxx
    /**
     * Creates a mapping between an {@code Activity} and it's {@code Contact}, if it possesses one.
     */
    private void addActivityMapping(Activity act) {
        if (act.getContact().isPresent()) {
            Contact contact = act.getContact().get();
            if (!hasContact(contact)) {
                addContact(contact);
            }
            contactActivityMap.put(contact, act);
        }
    }

    //@@author KxxMxxx
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

    //@@author KxxMxxx
    /**
     * Removes the mapping of an {@code Activity} in all the relevant {@code Contact} related {@code HashMap}.
     */
    private void removeActivityMapping(Activity act) {
        if (act.getContact().isPresent()) {
            Contact contact = getContact(act.getContact().get()).get();
            if (contactAccommodationMap.containsKey(contact) && contactActivityMap.containsKey(contact)) {
                //if contact linked to both
                contactActivityMap.remove(contact);
            } else if (contactActivityMap.containsKey(contact) && !contactAccommodationMap.containsKey(contact)) {
                //if linked only to activity
                removeContact(contact);
            }
        }
    }

    //@@author KxxMxxx
    /**
     * Removes the mapping of an {@code Accommodation} in all the relevant {@code Contact} related {@code HashMap}.
     */
    private void removeAccommodationMapping(Accommodation acc) {
        if (acc.getContact().isPresent()) {
            Contact contact = getContact(acc.getContact().get()).get();
            if (contactActivityMap.containsKey(contact) && contactAccommodationMap.containsKey(contact)) {
                //if contact linked to both
                contactAccommodationMap.remove(contact);
            } else if (contactAccommodationMap.containsKey(contact) && !contactActivityMap.containsKey(contact)) {
                //if only linked to accommodation
                removeContact(contact);
            }
        }
    }

    //@@author KxxMxxx
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

    //@@author KxxMxxx
    /**
     * Removes the mapping of an {@code Activity} in the {@code Day} related {@code HashMap} when an activity is
     * undscheduled.
     */
    private void removeDayMapping(Activity act, Day day) {
        if (activityDayMap.containsKey(act)) {
            activityDayMap.get(act).remove(day);
        }
    }

    //@@author KxxMxxx
    /**
     * Creates a mapping between an {@code Accommodation} and it's {@code Contact}, if it possesses one.
     */
    private void addAccommodationMapping(Accommodation acc) {
        if (acc.getContact().isPresent()) {
            Contact contact = acc.getContact().get();
            if (!hasContact(contact)) {
                addContact(contact);
            }
            contactAccommodationMap.put(contact, acc);
        }
    }

    //@@author KxxMxxx
    /**
     * Removes the mapping of a {@code Contact} and it's related {@code Activity} and {@code Accommodation}.
     */
    private void removeContactMapping(Contact contact) {
        if (contactActivityMap.containsKey(contact)) {
            Activity act = contactActivityMap.remove(contact);

            Activity newAct = new Activity(act.getName(), act.getAddress(), null,
                    act.getCost().isPresent() ? act.getCost().get() : null, act.getTags(), act.getDuration(),
                    act.getPriority());
            this.activities.setActivity(act, newAct);
        }

        if (contactAccommodationMap.containsKey(contact)) {
            Accommodation acc = contactAccommodationMap.remove(contact);
            Accommodation newAcc = new Accommodation(acc.getName(), acc.getAddress(), null, acc.getTags());
            this.accommodations.setAccommodation(acc, newAcc);
        }
    }

    //@@author KxxMxxx
    /**
     * Updates the mapping when there is a change to an {@code Activity}.  When the {@code Activity} is changed,
     * the {@code Day} containing it is updated.
     */
    private void updateDay(Activity oldAct, Activity newAct) throws EndOfTimeException {
        List<Day> listOfDays = activityDayMap.get(oldAct);
        List<Day> newListOfDays = new ArrayList<>();

        if (!listOfDays.isEmpty()) {
            itinerary.getItinerary().forEach(day -> {
                if (listOfDays.contains(day)) {
                    Day newDay = itinerary.updateDayActivity(oldAct, newAct, day);
                    setDay(day, newDay);
                    newListOfDays.add(newDay);
                }
            });
        }
        activityDayMap.remove(oldAct);
        activityDayMap.put(newAct, newListOfDays);
    }

    //@@author KxxMxxx
    /**
     * Updates the mapping when there is a change to an {@code Activity}. When the {@code Contact} of the
     * {@code Activity} is changed, the {@code Contact} is also updated.
     */
    private void updateMapping(Activity oldAct, Activity newAct) throws EndOfTimeException {
        if (oldAct.getContact().isPresent()) { //checks for existing mapping
            Contact oldContact = oldAct.getContact().get();

            if (contactAccommodationMap.containsKey(oldContact) && contactActivityMap.containsKey(oldContact)) {
                //if it is linked to both
                contactActivityMap.remove(oldContact);
            } else if (contactActivityMap.containsKey(oldContact) && !contactAccommodationMap.containsKey(oldContact)) {
                //if linked to just activity
                removeContact(oldContact);
            }

            if (newAct.getContact().isPresent()) {
                Contact newContact = newAct.getContact().get();
                if (!hasContact(newContact)) {
                    addContact(newContact);
                }
                contactActivityMap.put(newContact, newAct);
            }
        } else if (newAct.getContact().isPresent()) { //activity gets new contact previously not there
            Contact newContact = newAct.getContact().get();
            if (!hasContact(newContact)) {
                addContact(newContact);
            }
            addActivityMapping(newAct);
        }
        if (activityDayMap.containsKey(oldAct)) { //updates the activities in a day
            updateDay(oldAct, newAct);
        }
    }

    //@@author KxxMxxx
    /**
     * Updates the mapping when there is a change to an {@code Activity}. When the {@code Contact} of the
     * {@code Activity} is changed, the {@code Contact} is also updated.
     */
    private void updateMapping(Accommodation oldAcc, Accommodation newAcc) {
        if (oldAcc.getContact().isPresent()) { //checks for existing mapping
            Contact oldContact = oldAcc.getContact().get();
            if (contactActivityMap.containsKey(oldContact) && contactAccommodationMap.containsKey(oldContact)) {
                //if linked to both
                contactAccommodationMap.remove(oldContact);
            } else if (contactAccommodationMap.containsKey(oldContact) && !contactActivityMap.containsKey(oldContact)) {
                removeContact(oldContact);
            }
            if (newAcc.getContact().isPresent()) {
                Contact newContact = newAcc.getContact().get();
                if (!hasContact(newContact)) {
                    addContact(newContact);
                }
                contactAccommodationMap.put(newContact, newAcc);
            }
        } else if (newAcc.getContact().isPresent()) { //activity gets new contact previously not there
            Contact newContact = newAcc.getContact().get();
            if (!hasContact(newContact)) {
                addContact(newContact);
            }
            addAccommodationMapping(newAcc);
        }
    }

    //@@author KxxMxxx
    /**
     * Updates the mapping when there is a change to a {@code Contact}. When the {@code Contact} changes, the
     * {@code Activity} and {@code Accommodation} sharing the same contact is also updated.
     */
    private void updateMapping(Contact oldContact, Contact newContact) {
        if (contactActivityMap.containsKey(oldContact)) {
            Activity oldAct = contactActivityMap.remove(oldContact);
            Activity newAct = new Activity(oldAct.getName(), oldAct.getAddress(), newContact,
                    oldAct.getCost().orElse(null), oldAct.getTags(), oldAct.getDuration(),
                    oldAct.getPriority());
            activities.setActivity(oldAct, newAct);
            contactActivityMap.put(newContact, newAct);
        }
        if (contactAccommodationMap.containsKey(oldContact)) {
            Accommodation oldAcc = contactAccommodationMap.remove(oldContact);
            Accommodation newAcc = new Accommodation(oldAcc.getName(), oldAcc.getAddress(), newContact,
                    oldAcc.getTags());
            accommodations.setAccommodation(oldAcc, newAcc);
            contactAccommodationMap.put(newContact, newAcc);
        }
    }

    //@@author KxxMxxx
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

    //@@author KxxMxxx
    /**
     * Clears all mappings related to {@code Accommodation} and re-populates the mappings.
     */
    private void redoAccommodationMapping(List<Accommodation> accommodations) {
        contactAccommodationMap.clear();
        populateAccommodationMap(accommodations);
    }

    //@@author KxxMxxx
    /**
     * Clears all mappings related to {@code Activity} and re-populates the mappings.
     */
    private void redoActivityMapping(List<Activity> activities) {
        contactActivityMap.clear();
        populateActivityMap(activities);
    }

    //@@author KxxMxxx
    /**
     * A method to map a new contact to existing Activity or Accommodation if they share the same name and address
     * fields.
     */
    private void redoContactMapping(Contact contact) {
        if (contact.getAddress().isPresent()) {
            Name name = contact.getName();
            Address address = contact.getAddress().get();
            Activity activityToMap = this.activities.getActivity(name, address).orElse(null);
            Accommodation accommodationToMap = this.accommodations.getAccommodation(name, address).orElse(null);

            if (activityToMap != null) {
                Activity newActivity = new Activity(activityToMap.getName(), activityToMap.getAddress(), contact,
                        activityToMap.getCost().orElse(null), activityToMap.getTags(), activityToMap.getDuration(),
                        activityToMap.getPriority());
                this.activities.setActivity(activityToMap, newActivity);
                contactActivityMap.put(contact, newActivity);
            }
            if (accommodationToMap != null) {
                Accommodation newAccommodation = new Accommodation(accommodationToMap.getName(),
                        accommodationToMap.getAddress(), contact, activityToMap.getTags());
                this.accommodations.setAccommodation(accommodationToMap, newAccommodation);
                contactAccommodationMap.put(contact, newAccommodation);
            }
        }
    }

    /**
     * Clears all the mappings related to {@code Day} and re-populates the mappings.
     */
    private void redoDayMapping(List<Day> days) {
        activityDayMap.clear();
        populateDayMap(days);
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
    public Path getPlannerFilePath() {
        return userPrefs.getPlannerFilePath();
    }

    @Override
    public void setPlannerFilePath(Path plannerFilePath) {
        requireNonNull(plannerFilePath);
        userPrefs.setPlannerFilePath(plannerFilePath);
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
        redoAccommodationMapping(accommodations.getAccommodationList());
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
        removeAccommodationMapping(target);
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
        addAccommodationMapping(accommodation);
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
        redoActivityMapping(activities.getActivityList());
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
        addActivityMapping(activity);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    @Override
    public void setActivity(Activity target, Activity editedActivity) throws EndOfTimeException {
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
        removeContact(target);
    }

    private void removeContact(Contact target) {
        contacts.removeContact(target);
    }

    @Override
    public void addContact(Contact contact) {
        contacts.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
    }

    @Override
    public void addContactAtIndex(Index index, Contact contact) {
        redoContactMapping(contact);
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
    public Optional<Contact> getContact(Contact toGet) {
        requireNonNull(toGet);
        return contacts.getContact(toGet);
    }

    //=========== Itinerary ================================================================================
    //@@author 1nefootstep
    @Override
    public Name getName() {
        return this.itinerary.getName();
    }

    //@@author 1nefootstep
    @Override
    public void setItineraryName(Name name) {
        this.itinerary.setName(name);
    }

    //@@author 1nefootstep
    @Override
    public LocalDate getStartDate() {
        return this.itinerary.getStartDate();
    }

    //@@author 1nefootstep
    @Override
    public LocalDateTime getLastDateTime() {
        return this.itinerary.getLastDateTime();
    }

    //@@author 1nefootstep
    @Override
    public void setItineraryStartDate(LocalDate date) {
        this.itinerary.setStartDate(date);
    }

    //@@author 1nefootstep
    @Override
    public void shiftDatesInItineraryByDay(long days) {
        shiftDatesInItineraryByDayBetweenRange(
                days,
                Index.fromOneBased(1),
                Index.fromOneBased(itinerary.getNumberOfDays() + 1)
        );
    }

    //@@author 1nefootstep
    @Override
    public void shiftDatesInItineraryByDayBetweenRange(long days, Index startIndex, Index endIndex) {
        this.itinerary.shiftDatesInItinerary(days, startIndex, endIndex);
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
        redoDayMapping(itinerary);
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
        day.removeActivityWithIndex(toRemove);
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
