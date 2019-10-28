//@@author SakuraBlossom
package seedu.address.model;

import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.OmniPanelTab;
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
    boolean hasPatient(Person person);

    /**
     * Returns true if an exact {@code person} exists in the address book.
     */
    boolean hasExactPatient(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePatient(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPatient(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPatient(Person target, Person editedPerson);


    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the filtered patient list
     */
    ObservableList<Person> getFilteredPatientList();

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatientList(Predicate<Person> predicate);


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
    boolean isPatientInQueue(ReferenceId id);

    /**
     * Checks if the patient is in queue
     */
    void changePatientRefIdInQueue(ReferenceId idToEdit, ReferenceId editedId);

    /**
     * Returns the queueList
     */
    ObservableList<ReferenceId> getQueueList();

    /**
     * Adds a new room to the list of rooms
     */
    void addRoom(Room room);

    /**
     * Removes a room
     */
    void removeRoom(Room target);

    /**
     * Checks if the room exists
     */
    boolean hasRoom(Room room);

    ObservableList<Room> getConsultationRoomList();

    //=========== Appointment Scheduler ======================================================================

    /**
     * Replaces schedule data with the data in {@code schedule}.
     */
    void setAppointmentSchedule(ReadOnlyAppointmentBook schedule);

    /**
     * Returns the schedule of appointments.
     */
    ReadOnlyAppointmentBook getAppointmentBook();

    /**
     * Returns true if an appointment with the same identity as {@code event} exists in the schedule.
     */
    boolean hasAppointment(Event event);

    /**
     * Returns true if an exact {@code event} exists in the schedule.
     */
    boolean hasExactAppointment(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the schedule.
     */
    void deleteAppointment(Event event);

    /**
     * Adds the given event.
     * {@code person} must not already exist in the schedule.
     */
    void addAppointment(Event event);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the schedule.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the address book.
     */
    void setAppointment(Event target, Event editedEvent);


    //=========== Filtered Appointment List Accessors ==============================================================

    /**
     * Returns an unmodifiable view of the filtered appointment list
     */
    ObservableList<Event> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Event> predicate);

    Boolean isPatientList();

    Boolean isMissedList();

    //=========== Duty Roster Scheduler ======================================================================

    /**
     * Replaces schedule data with the data in {@code schedule}.
     */
    void setDutyShiftSchedule(ReadOnlyAppointmentBook schedule);

    /**
     * Returns the schedule of appointments.
     */
    ReadOnlyAppointmentBook getDutyShiftBook();

    /**
     * Returns true if an appointment with the same identity as {@code event} exists in the schedule.
     */
    boolean hasDutyShift(Event event);

    /**
     * Returns true if an exact {@code event} exists in the schedule.
     */
    boolean hasExactDutyShift(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the schedule.
     */
    void deleteDutyShift(Event event);

    /**
     * Adds the given event.
     * {@code person} must not already exist in the schedule.
     */
    void addDutyShift(Event event);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the schedule.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the address book.
     */
    void setDutyShift(Event target, Event editedEvent);


    //=========== Filtered DutyShift List Accessors ==============================================================

    /**
     * Returns an unmodifiable view of the filtered appointment list
     */
    ObservableList<Event> getFilteredDutyShiftList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDutyShiftList(Predicate<Event> predicate);

    //=========== User Interface =============================================================================

    /**
     * Sets the desired tab listing.
     */
    void setTabListing(OmniPanelTab tab);

    /**
     * Binds the OmniPanel tab selector.
     */
    void bindTabListingCommand(Consumer<OmniPanelTab> tabConsumer);
}
