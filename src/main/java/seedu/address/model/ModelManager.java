//@@author SakuraBlossom
package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.OmniPanelTab;
import seedu.address.model.events.AppointmentBook;
import seedu.address.model.events.Event;
import seedu.address.model.person.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.queue.QueueManager;
import seedu.address.model.queue.Room;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;
import seedu.address.model.userprefs.UserPrefs;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;

    private final AppointmentBook appointmentBook;
    private final AppointmentBook dutyRosterBook;
    private final AddressBook staffAddressBook;
    private final AddressBook patientAddressBook;

    private final FilteredList<Person> filteredPatients;
    private final FilteredList<Person> filteredStaff;
    private final FilteredList<Event> filteredAppointments;
    private final FilteredList<Event> filteredDutyShifts;

    private final QueueManager queueManager;
    private final ObservableList<Room> consultationRooms;
    private final ObservableList<ReferenceId> patientQueueList;

    private Consumer<OmniPanelTab> omniPanelTabConsumer;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook patientAddressBook, ReadOnlyAddressBook staffAddressBook,
                        ReadOnlyAppointmentBook patientSchedule, ReadOnlyAppointmentBook dutyRoster,
                        ReadOnlyUserPrefs userPrefs, QueueManager queueManager) {
        super();
        requireAllNonNull(patientAddressBook, userPrefs);
        logger.fine("Initializing with"
            + "\nLocal patient address book data file location : " + patientAddressBook
            + "\nLocal staff details data file location : " + staffAddressBook
            + "\nLocal appointment data file location : " + patientSchedule
            + "\nLocal duty roster data file location : " + dutyRoster
            + "\nUser prefs: " + userPrefs);

        this.staffAddressBook = new AddressBook(staffAddressBook);
        this.patientAddressBook = new AddressBook(patientAddressBook);

        this.appointmentBook = new AppointmentBook(patientSchedule);
        this.dutyRosterBook = new AppointmentBook(dutyRoster);

        this.userPrefs = new UserPrefs(userPrefs);
        this.queueManager = new QueueManager(queueManager);

        this.filteredPatients = new FilteredList<>(this.patientAddressBook.getPersonList());
        this.filteredStaff = new FilteredList<>(this.staffAddressBook.getPersonList());

        this.filteredAppointments = new FilteredList<>(this.appointmentBook.getEventList());
        this.filteredDutyShifts = new FilteredList<>(this.dutyRosterBook.getEventList());

        this.consultationRooms = new FilteredList<>(this.queueManager.getRoomList());
        this.patientQueueList = new FilteredList<>(this.queueManager.getReferenceIdList());

        this.omniPanelTabConsumer = null;
    }

    public ModelManager() {
        this(new AddressBook(), new AddressBook(),
            new AppointmentBook(), new AppointmentBook(),
            new UserPrefs(), new QueueManager());
    }

    //=========== User Interface =============================================================================

    @Override
    public void setTabListing(OmniPanelTab tab) {
        requireNonNull(tab);
        if (omniPanelTabConsumer != null) {
            omniPanelTabConsumer.accept(tab);
        }
    }

    @Override
    public void bindTabListingCommand(Consumer<OmniPanelTab> tabConsumer) {
        this.omniPanelTabConsumer = tabConsumer;
    }

    //=========== QueueManager ==================================================================================

    @Override
    public QueueManager getQueueManager() {
        return queueManager;
    }

    public void removeFromQueue(ReferenceId target) {
        queueManager.removePatient(target);
    }

    @Override
    public void enqueuePatient(ReferenceId id) {
        queueManager.addPatient(id);
    }

    @Override
    public void enqueuePatientToIndex(ReferenceId id, int index) {
        queueManager.addPatient(index, id);
    }

    @Override
    public boolean isPatientInQueue(ReferenceId id) {
        requireNonNull(id);
        return queueManager.hasId(id);
    }

    @Override
    public void changePatientRefIdInQueue(ReferenceId idToEdit, ReferenceId editedId) {
        queueManager.setPatientInQueue(idToEdit, editedId);
    }

    @Override
    public void addRoom(Room room) {
        queueManager.addRoom(room);
    }

    @Override
    public void removeRoom(Room target) {
        queueManager.removeRoom(target);
    }

    @Override
    public boolean hasRoom(Room room) {
        return queueManager.hasRoom(room);
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


    //=========== Patient AddressBook ================================================================================

    @Override
    public void setPatientAddressBook(ReadOnlyAddressBook patientAddressBook) {
        this.patientAddressBook.resetData(patientAddressBook);
    }

    @Override
    public ReadOnlyAddressBook getPatientAddressBook() {
        return patientAddressBook;
    }

    @Override
    public boolean hasPatient(ReferenceId id) {
        requireNonNull(id);
        return patientAddressBook.hasPerson(id);
    }

    @Override
    public boolean hasPatient(Person person) {
        requireNonNull(person);
        return patientAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasExactPatient(Person person) {
        requireNonNull(person);
        return patientAddressBook.hasExactPerson(person);
    }

    @Override
    public void deletePatient(Person target) {
        patientAddressBook.removePerson(target);
    }

    @Override
    public void addPatient(Person person) {
        patientAddressBook.addPerson(person);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPatient(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        patientAddressBook.setPerson(target, editedPerson);
    }

    @Override
    public Person resolvePatient(ReferenceId id) {
        return patientAddressBook.resolve(id);
    }


    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPatientList() {
        return filteredPatients;
    }

    @Override
    public void updateFilteredPatientList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
    }

    //=========== Patient AddressBook ================================================================================

    @Override
    public void setStaffAddressBook(ReadOnlyAddressBook staffAddressBook) {
        this.staffAddressBook.resetData(staffAddressBook);
    }

    @Override
    public ReadOnlyAddressBook getStaffAddressBook() {
        return staffAddressBook;
    }

    @Override
    public boolean hasStaff(ReferenceId id) {
        requireNonNull(id);
        return staffAddressBook.hasPerson(id);
    }

    @Override
    public boolean hasStaff(Person person) {
        requireNonNull(person);
        return staffAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasExactStaff(Person person) {
        requireNonNull(person);
        return staffAddressBook.hasExactPerson(person);
    }

    @Override
    public void deleteStaff(Person target) {
        requireNonNull(target);
        staffAddressBook.removePerson(target);
    }

    @Override
    public void addStaff(Person person) {
        requireNonNull(person);
        staffAddressBook.addPerson(person);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setStaff(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        staffAddressBook.setPerson(target, editedPerson);
    }

    @Override
    public Person resolveStaff(ReferenceId id) {
        requireNonNull(id);
        return staffAddressBook.resolve(id);
    }


    //=========== Filtered Staff List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredStaffList() {
        return filteredStaff;
    }

    @Override
    public void updateFilteredStaffList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredStaff.setPredicate(predicate);
    }


    //=========== Queue List Accessors ========================================================
    @Override
    public ObservableList<ReferenceId> getQueueList() {
        return patientQueueList;
    }


    //=========== Filtered Room List Accessors =============================================================

    @Override
    public ObservableList<Room> getConsultationRoomList() {
        return consultationRooms;
    }

    @Override
    public void setAppointmentSchedule(ReadOnlyAppointmentBook schedule) {
        this.appointmentBook.resetData(schedule);
    }

    @Override
    public ReadOnlyAppointmentBook getAppointmentBook() {
        return appointmentBook;
    }

    @Override
    public boolean hasAppointment(Event event) {
        requireNonNull(event);
        return appointmentBook.hasEvent(event);
    }

    @Override
    public boolean hasExactAppointment(Event event) {
        requireNonNull(event);
        return appointmentBook.hasExactEvent(event);
    }

    @Override
    public void deleteAppointment(Event event) {
        appointmentBook.removeEvent(event);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void addAppointment(Event event) {
        appointmentBook.addEvent(event);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setAppointment(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        appointmentBook.setEvent(target, editedEvent);
    }


    //=========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Event> getFilteredAppointmentList() {
        return filteredAppointments;
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }

    /**
     * Returns an boolean, check whether current displaying appointments are belong to the same patient.
     */
    @Override
    public Boolean isPatientList() {
        requireNonNull(filteredAppointments);
        boolean res = true;
        ReferenceId id = filteredAppointments.get(0).getPersonId();
        for (Event e : filteredAppointments) {
            if (!id.equals(e.getPersonId())) {
                res = false;
                break;
            }
        }
        return res;
    }

    @Override
    public Boolean isMissedList() {
        requireNonNull(filteredAppointments);
        boolean res = true;
        for (Event e : filteredAppointments) {
            if (!e.getStatus().isMissed()) {
                res = false;
                break;
            }
        }
        return res;
    }

    @Override
    public void setDutyShiftSchedule(ReadOnlyAppointmentBook schedule) {
        requireNonNull(schedule);
        dutyRosterBook.resetData(schedule);
    }

    @Override
    public ReadOnlyAppointmentBook getDutyShiftBook() {
        return dutyRosterBook;
    }

    @Override
    public boolean hasDutyShift(Event event) {
        requireNonNull(event);
        return dutyRosterBook.hasEvent(event);
    }

    @Override
    public boolean hasExactDutyShift(Event event) {
        requireNonNull(event);
        return dutyRosterBook.hasExactEvent(event);
    }

    @Override
    public void deleteDutyShift(Event event) {
        requireNonNull(event);
        dutyRosterBook.removeEvent(event);
    }

    @Override
    public void addDutyShift(Event event) {
        requireNonNull(event);
        dutyRosterBook.addEvent(event);
    }

    @Override
    public void setDutyShift(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);
        dutyRosterBook.setEvent(target, editedEvent);
    }

    @Override
    public ObservableList<Event> getFilteredDutyShiftList() {
        return filteredDutyShifts;
    }

    @Override
    public void updateFilteredDutyShiftList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredDutyShifts.setPredicate(predicate);
    }


    //=========== Misc =======================================================================================

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
        return userPrefs.equals(other.userPrefs)
                && patientAddressBook.equals(other.patientAddressBook)
                && staffAddressBook.equals(other.staffAddressBook)
                && appointmentBook.equals(other.appointmentBook)
                && dutyRosterBook.equals(other.dutyRosterBook)
                && filteredPatients.equals(other.filteredPatients)
                && filteredStaff.equals(other.filteredStaff)
                && filteredAppointments.equals(other.filteredAppointments)
                && filteredDutyShifts.equals(other.filteredDutyShifts)
                && queueManager.equals(other.queueManager)
                && consultationRooms.equals(other.consultationRooms)
                && patientQueueList.equals(other.patientQueueList);
    }
}


