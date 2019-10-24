package seedu.address.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Phone;
import seedu.address.model.day.Day;
import seedu.address.model.field.Name;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Accommodation> PREDICATE_SHOW_ALL_ACCOMMODATIONS = unused -> true;
    Predicate<Activity> PREDICATE_SHOW_ALL_ACTIVITIES = unused -> true;
    Predicate<Contact> PREDICATE_SHOW_ALL_CONTACTS = unused -> true;
    Predicate<Day> PREDICATE_SHOW_ALL_DAYS = unused -> true;

    //=========== UserPrefs ==================================================================================

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
     * Returns the user prefs' accommodation file path.
     */
    Path getAccommodationFilePath();

    /**
     * Sets the user prefs' accommodation file path.
     */
    void setAccommodationFilePath(Path accommodationFilePath);

    /**
     * Returns the user prefs' activity file path.
     */
    Path getActivityFilePath();

    /**
     * Sets the user prefs' activity file path.
     */
    void setActivityFilePath(Path activtyFilePath);

    /**
     * Returns the user prefs' contact file path.
     */
    Path getContactFilePath();

    /**
     * Sets the user prefs' contact file path.
     */
    void setContactFilePath(Path contactFilePath);

    /**
     * Returns the user prefs' itinerary file path.
     */
    Path getItineraryFilePath();

    /**
     * Sets the user prefs' itinerary file path.
     */
    void setItineraryFilePath(Path itineraryFilePath);

    //=========== AccommodationManager =========================================================================

    /**
     * Replaces current AccommodationManager data with the data in {@code accommodations}.
     */
    void setAccommodations(ReadOnlyAccommodation accommodations);

    /**
     * Returns the AccommodationManager
     */
    ReadOnlyAccommodation getAccommodations();

    /**
     * Returns true if an accommodation with the same identity as {@code accommodation} exists in the
     * AccommodationManager.
     */
    boolean hasAccommodation(Accommodation accommodation);

    /**
     * Deletes the given accommodation.
     * The accommodation must exist in the AccommodationManager.
     */
    void deleteAccommodation(Accommodation target);

    /**
     * Adds the given accommodation.
     * {@code contacts} must not already exist in the AccommodationManager.
     */
    void addAccommodation(Accommodation accommodation);

    /**
     * Replaces the given accommodation {@code target} with {@code editedAccommodation}.
     * {@code target} must exist in the AccommodationManager.
     * The accommodation identity of {@code editedAccommodatino} must not be the same as another existing
     * accommodations in the AccommodationManager.
     */
    void setAccommodation(Accommodation target, Accommodation editedAccommodation);

    /**
     * Returns an unmodifiable view of the filtered Accommodation list
     */
    ObservableList<Accommodation> getFilteredAccommodationList();

    /**
     * Updates the filter of the filtered Accommodation list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAccommodationList(Predicate<Accommodation> predicate);

    //=========== ActivityManager ============================================================================

    /**
     * Replaces current ActivityManager data with the data in {@code activities}.
     */
    void setActivities(ReadOnlyActivity activities);

    /**
     * Returns the ActivityManager
     */
    ReadOnlyActivity getActivities();

    /**
     * Returns true if a activity with the same identity as {@code activity} exists in the ActivityManager.
     */
    boolean hasActivity(Activity activity);

    /**
     * Deletes the given activity.
     * The activity must exist in the ActivityManager.
     */
    void deleteActivity(Activity target);

    /**
     * Adds the given activity.
     * {@code activity} must not already exist in the ActivityManager.
     */
    void addActivity(Activity activity);

    /**
     * Replaces the given activity {@code target} with {@code editedActivity}.
     * {@code target} must exist in the ActivityManager.
     * The activity identity of {@code editedActivity} must not be the same as another existing activities in the
     * ActivityManager.
     */
    void setActivity(Activity target, Activity editedActivity);

    /**
     * Returns an unmodifiable view of the filtered activities list
     */
    ObservableList<Activity> getFilteredActivityList();

    /**
     * Updates the filter of the filtered activity list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredActivityList(Predicate<Activity> predicate);

    //=========== ContactManager =============================================================================

    /**
     * Replaces current ContactManager data with the data in {@code contacts}.
     */
    void setContacts(ReadOnlyContact contacts);

    /**
     * Returns the ContactManager
     */
    ReadOnlyContact getContacts();

    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the contact list.
     */
    boolean hasContact(Contact contact);

    /**
     * Returns true if a contact with the same phone as {@code phone} exists in the contact list.
     */
    boolean hasPhone(Phone phone);

    /**
     * Deletes the given contacts.
     * The contacts must exist in the contact list.
     */
    void deleteContact(Contact target);

    /**
     * Adds the given contacts.
     * {@code contacts} must not already exist in the contact list.
     */
    void addContact(Contact contact);

    /**
     * Replaces the given contacts {@code target} with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contacts identity of {@code editedContact} must not be the same as another existing contacts in the
     * contact list.
     */
    void setContact(Contact target, Contact editedContact);

    /**
     * Returns an Optional Contact by searching the contact list for a contact with the same phone as {@code phone}.
     */
    Optional<Contact> getContactByPhone(Phone toGet);

    /**
     * Returns an unmodifiable view of the filtered contacts list
     */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Updates the filter of the filtered contacts list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContactList(Predicate<Contact> predicate);

    //=========== Itinerary ================================================================================

    /**
     * Retrieves the Name of the Itinerary
     * @return Name of itinerary
     */
    Name getName();

    /**
     * Sets a new Name for the Itinerary
     * @param name new name to be set
     */
    void setItineraryName(Name name);

    /**
     * Retrieves the start date of the Itinerary
     * @return start date of Itinerary
     */
    LocalDate getStartDate();

    /**
     * Sets a new StartDate for the Itinerary
     * @param date new start date to be set
     */
    void setItineraryStartDate(LocalDate date);

    /**
     * Replaces current Itinerary data with the data in {@code itinerary}.
     */
    void setItinerary(ReadOnlyItinerary itinerary);

    /**
     * Returns the Itinerary
     */
    ReadOnlyItinerary getItinerary();

    /**
     * Returns true if a day with the same identity as {@code day} exists in the itinerary.
     */
    boolean hasDay(Day day);

    void deleteDay(Day target);

    void addDays(int n);

    void setDays(List<Day> itinerary);

    /**
     * Returns an unmodifiable view of the filtered itinerary
     */
    ObservableList<Day> getFilteredItinerary();

    /**
     * Updates the filter of the filtered itinerary to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredItinerary(Predicate<Day> predicate);
}
