//@@author SakuraBlossom
package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.common.ReferenceIdResolver;
import seedu.address.model.events.Event;
import seedu.address.model.person.Person;
import seedu.address.model.queue.QueueManager;
import seedu.address.model.queue.Room;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;

/**
 * The API of the Model component.
 */
public interface Model extends ReferenceIdResolver {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;
    Predicate<Room> PREDICATE_SHOW_ALL_ROOMS = unused -> true;


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


    //=========== Patient AddressBook ================================================================================

    /**
     * Replaces address book data with the data in {@code patientAddressBook}.
     */
    void setPatientAddressBook(ReadOnlyAddressBook patientAddressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getPatientAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if an exact {@code person} exists in the address book.
     */
    boolean hasExactPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);


    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    //=========== Staff AddressBook ================================================================================

    /**
     * Replaces address book data with the data in {@code staffAddressBook}.
     */
    void setStaffAddressBook(ReadOnlyAddressBook staffAddressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getStaffAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the staff address book.
     */
    boolean hasStaff(Person person);

    /**
     * Returns true if an exact {@code person} exists in the staff address book.
     */
    boolean hasExactStaff(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the staff address book.
     */
    void deleteStaff(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the staff address book.
     */
    void addStaff(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the staff address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setStaff(Person target, Person editedPerson);


    //=========== Filtered Person List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered staff list */
    ObservableList<Person> getFilteredStaffList();

    /**
     * Updates the filter of the filtered staff list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStaffList(Predicate<Person> predicate);

    //=========== Queue Manager ==================================================================================

    /**
     * Returns the QueueManager
     */
    QueueManager getQueueManager();

    /**
     * Removes a patient from the queue with the ReferenceId given
     */
    void removeFromQueue(ReferenceId target);

    /**
     * Enqueues a patient to the queue
     */
    void enqueuePatient(ReferenceId id);

    /**
     * Enqueues a patient to the queue based on the index given
     */
    void enqueuePatientToIndex(ReferenceId id, int index);

    /**
     * Checks if the patient is in queue
     */
    public boolean isPatientInQueue(ReferenceId id);

    /**
     * Returns the queueList
     */
    ObservableList<ReferenceId> getQueueList();

    /**
     * Adds a new room to the list of rooms
     */
    void addRoom(Room room);

    /**
     * Adds a new room to the list of rooms based on the index
     */
    void addRoomToIndex(Room room, int indexOfRoom);

    /**
     * Removes a room
     */
    void removeRoom(Room target);

    /**
     * Checks if the room exists
     */
    public boolean hasRoom(Room room);

    ObservableList<Room> getConsultationRoomList();

    //=========== Scheduler ==================================================================================

    /**
     * Replaces schedule data with the data in {@code schedule}.
     */
    void setSchedule(ReadOnlyAppointmentBook schedule);

    /**
     * Returns the schedule of appointments.
     */
    ReadOnlyAppointmentBook getAppointmentBook();

    /**
     * Returns true if an event with the same identity as {@code event} exists in the schedule.
     */
    boolean hasEvent(Event event);

    /**
     * Returns true if an exact {@code event} exists in the schedule.
     */
    boolean hasExactEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the schedule.
     */
    void deleteEvent(Event event);

    /**
     * Adds the given event.
     * {@code person} must not already exist in the schedule.
     */
    void addEvent(Event event);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the schedule.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the address book.
     */
    void setEvent(Event target, Event editedEvent);


    //=========== Filtered Event List Accessors ==============================================================

    /**
     * Returns an unmodifiable view of the filtered event list
     */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    void updateFilteredEventList(ReferenceId referenceId);

    void updateFilteredEventList();

    void updateToMissedEventList();

    void displayApprovedAndAckedPatientEvent(ReferenceId referenceId);

    Boolean isPatientList();

    Boolean isMissedList();
}
