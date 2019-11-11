//@@author SakuraBlossom
package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.ListIterator;
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
import seedu.address.model.events.exceptions.InvalidEventScheduleChangeException;
import seedu.address.model.exceptions.EntryNotFoundException;
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
    public static final String MESSAGE_SCHEDULE_APPOINTMENT_FOR_STAFF =
            "Scheduling staff doctors for appointments is current unsupported.";
    public static final String MESSAGE_NOT_ENOUGH_STAFF_FOR_ADD =
            "Insufficient staff doctor(s) on duty from %1$s.\n"
                    + "All %2$d staff doctor(s) have been assigned an appointment on the given timing.\n"
                    + "Please provide another valid appointment's timing.";
    public static final String MESSAGE_NOT_ENOUGH_STAFF_FOR_CANCEL =
            "Insufficient staff doctor(s) on duty from %1$s.\n"
                    + "All %2$d staff doctor(s) have been assigned an appointment.\n"
                    + "Please provide another valid duty shift's timing.";
    public static final String MESSAGE_NOT_OVERLAPPING_DUTYSHIFT =
            "Doctor `%1$s` is already scheduled an existing duty shift from %2$s.";
    public static final String MESSAGE_NOT_OVERLAPPING_APPOINTMENT =
            "Patient `%1$s` is already scheduled an existing appointment from %2$s.";

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

        this.filteredAppointments = new FilteredList<>(this.appointmentBook.getEventList(), PREDICATE_SHOW_ALL_EVENTS);
        this.filteredDutyShifts = new FilteredList<>(this.dutyRosterBook.getEventList(), PREDICATE_SHOW_ALL_EVENTS);

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
        return queueManager.hasIdInQueue(id);
    }

    @Override
    public boolean isPatientBeingServed(ReferenceId id) {
        requireNonNull(id);
        return queueManager.hasIdInRooms(id);
    }

    @Override
    public boolean isDoctorOnDuty(ReferenceId id) {
        return queueManager.isDoctorOnDuty(id);
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
        appointmentBook.updatesPersonDetails(target, editedPerson);
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
        dutyRosterBook.updatesPersonDetails(target, editedPerson);
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
        return appointmentBook.hasEvent(event);
    }

    @Override
    public boolean hasExactAppointment(Event event) {
        return appointmentBook.hasExactEvent(event);
    }

    @Override
    public void deleteAppointment(Event appointment) {
        appointmentBook.removeEvent(appointment);
    }

    /**
     * Schedules a given {@code appointment}.
     *
     * @throws InvalidEventScheduleChangeException if the number of unique events which timings are in conflict
     *                                             is greater or equal to the {@code maxNumberOfConcurrentEvents}
     *                                             or the events in conflict involves the same patient given
     *                                             in {@code appointment}, but ignores {@code ignoreEventCase}
     */
    private void checkValidScheduleAppointment(Event appointment, Event ignoreEventCase)
            throws InvalidEventScheduleChangeException {
        int numOfAvailableStaff = getNumberOfDutyShiftInConflict(appointment);
        ListIterator<Event> itr = getAppointmentsInConflict(appointment);

        if (hasStaff(appointment.getPersonId())) {
            throw new InvalidEventScheduleChangeException(MESSAGE_SCHEDULE_APPOINTMENT_FOR_STAFF);
        }

        int countNumberOfConcurrentAppointments = 0;
        while (itr.hasNext()) {
            Event apt = itr.next();
            countNumberOfConcurrentAppointments++;
            if (appointment.getPersonId().isSameAs(apt.getPersonId()) && !apt.equals(ignoreEventCase)) {

                throw new InvalidEventScheduleChangeException(
                        String.format(MESSAGE_NOT_OVERLAPPING_APPOINTMENT, apt.getPersonName().toString(),
                                apt.getEventTiming().toString()));

            }
        }

        if (numOfAvailableStaff <= countNumberOfConcurrentAppointments
                && !(numOfAvailableStaff == countNumberOfConcurrentAppointments
                && ignoreEventCase != null
                && !appointment.conflictsWith(ignoreEventCase))) {

            throw new InvalidEventScheduleChangeException(
                    String.format(MESSAGE_NOT_ENOUGH_STAFF_FOR_ADD,
                            appointment.getEventTiming().toString(),
                            numOfAvailableStaff));
        }
    }

    @Override
    public void scheduleAppointment(Event appointment) throws InvalidEventScheduleChangeException {
        checkValidScheduleAppointment(appointment, null);
        appointmentBook.addEvent(appointment);
    }

    @Override
    public void scheduleAppointments(List<Event> appointments) throws InvalidEventScheduleChangeException {
        for (Event e : appointments) {
            checkValidScheduleAppointment(e, null);
        }

        for (Event e : appointments) {
            appointmentBook.addEvent(e);
        }
    }

    @Override
    public void setAppointment(Event target, Event editedEvent) throws InvalidEventScheduleChangeException {
        requireAllNonNull(target, editedEvent);
        if (!hasExactAppointment(target)) {
            throw new EntryNotFoundException();
        }

        checkValidScheduleAppointment(editedEvent, target);
        deleteAppointment(target);
        appointmentBook.addEvent(editedEvent);
    }

    @Override
    public ListIterator<Event> getAppointmentsInConflict(Event toCheck) {
        return appointmentBook.getEventsInConflict(toCheck);
    }

    @Override
    public int getNumberOfAppointmentsInConflict(Event toCheck) {
        return appointmentBook.countNumberOfEventsInConflict(toCheck);
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

    @Override
    public Boolean isListingAppointmentsOfSinglePatient() {
        requireNonNull(filteredAppointments);

        if (filteredAppointments.size() == 0) {
            return false;
        }

        ReferenceId id = filteredAppointments.get(0).getPersonId();
        for (Event e : filteredAppointments) {
            if (!id.equals(e.getPersonId())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean isListingAppointmentsOfSingleStaff() {
        requireNonNull(filteredDutyShifts);

        if (filteredDutyShifts.size() == 0) {
            return false;
        }

        ReferenceId id = filteredDutyShifts.get(0).getPersonId();
        for (Event e : filteredDutyShifts) {
            if (!id.equals(e.getPersonId())) {
                return false;
            }
        }
        return true;
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
    public boolean hasDutyShift(Event dutyShift) {
        requireNonNull(dutyShift);
        return dutyRosterBook.hasEvent(dutyShift);
    }

    @Override
    public boolean hasExactDutyShift(Event dutyShift) {
        requireNonNull(dutyShift);
        return dutyRosterBook.hasExactEvent(dutyShift);
    }

    /**
     * Checks if a given {@code dutyShift} can be deleted.
     */
    private void checksCanDeleteDutyShift(Event dutyShift) throws InvalidEventScheduleChangeException {
        requireNonNull(dutyShift);
        if (!hasExactDutyShift(dutyShift)) {
            throw new EntryNotFoundException();
        }

        int numOfAvailableStaff = getNumberOfDutyShiftInConflict(dutyShift);
        int numOfAppointments = getNumberOfAppointmentsInConflict(dutyShift);

        if (numOfAvailableStaff <= numOfAppointments) {
            throw new InvalidEventScheduleChangeException(
                    String.format(MESSAGE_NOT_ENOUGH_STAFF_FOR_CANCEL,
                            dutyShift.getEventTiming().toString(),
                            numOfAvailableStaff));
        }
    }


    @Override
    public void deleteDutyShifts(Event dutyShift) throws InvalidEventScheduleChangeException {
        checksCanDeleteDutyShift(dutyShift);
        dutyRosterBook.removeEvent(dutyShift);
    }

    @Override
    public void deleteDutyShifts(List<Event> dutyShifts) throws InvalidEventScheduleChangeException {
        for (Event e : dutyShifts) {
            checksCanDeleteDutyShift(e);
        }

        for (Event e : dutyShifts) {
            dutyRosterBook.removeEvent(e);
        }
    }


    /**
     * Schedules a given {@code dutyShift}.
     *
     * @throws InvalidEventScheduleChangeException if the events in conflict
     *                                             involves the same staff member given in {@code dutyShift}
     */
    private void checkValidScheduleDutyShift(Event dutyShift, Event ignoreEventCase)
            throws InvalidEventScheduleChangeException {
        if (hasPatient(dutyShift.getPersonId())) {
            throw new InvalidEventScheduleChangeException(MESSAGE_SCHEDULE_APPOINTMENT_FOR_STAFF);
        }

        ListIterator<Event> itr = getDutyShiftInConflict(dutyShift);

        while (itr.hasNext()) {
            Event shift = itr.next();
            if (dutyShift.getPersonId().isSameAs(shift.getPersonId()) && !shift.equals(ignoreEventCase)) {
                throw new InvalidEventScheduleChangeException(
                        String.format(MESSAGE_NOT_OVERLAPPING_DUTYSHIFT, shift.getPersonName().toString(),
                                shift.getEventTiming().toString()));

            }
        }
    }

    @Override
    public void scheduleDutyShift(Event dutyShift) throws InvalidEventScheduleChangeException {
        checkValidScheduleDutyShift(dutyShift, null);
        dutyRosterBook.addEvent(dutyShift);
    }

    @Override
    public void scheduleDutyShift(List<Event> dutyShifts) throws InvalidEventScheduleChangeException {
        for (Event e : dutyShifts) {
            checkValidScheduleDutyShift(e, null);
        }

        for (Event e : dutyShifts) {
            dutyRosterBook.addEvent(e);
        }
    }

    @Override
    public void setDutyShift(Event target, Event editedEvent) throws InvalidEventScheduleChangeException {

        if (target.isSameAs(editedEvent)) {
            throw new InvalidEventScheduleChangeException(
                    String.format(MESSAGE_NOT_OVERLAPPING_DUTYSHIFT, target.getPersonName().toString(),
                            target.getEventTiming().toString()));

        }

        checksCanDeleteDutyShift(target);
        checkValidScheduleDutyShift(editedEvent, target);
        dutyRosterBook.removeEvent(target);
        dutyRosterBook.addEvent(editedEvent);
    }

    @Override
    public ListIterator<Event> getDutyShiftInConflict(Event toCheck) {
        return dutyRosterBook.getEventsInConflict(toCheck);
    }

    @Override
    public int getNumberOfDutyShiftInConflict(Event toCheck) {
        return dutyRosterBook.countNumberOfEventsInConflict(toCheck);
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


