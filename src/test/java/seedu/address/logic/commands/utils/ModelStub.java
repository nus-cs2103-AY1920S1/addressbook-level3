package seedu.address.logic.commands.utils;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.OmniPanelTab;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReferenceId;
import seedu.address.model.events.Event;
import seedu.address.model.person.Person;
import seedu.address.model.queue.QueueManager;
import seedu.address.model.queue.Room;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPatient(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPatientAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getPatientAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPatient(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPatient(ReferenceId person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasExactPatient(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePatient(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPatient(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPatientList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPatientList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStaffAddressBook(ReadOnlyAddressBook staffAddressBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getStaffAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStaff(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStaff(ReferenceId id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasExactStaff(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStaff(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStaff(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStaff(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredStaffList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredStaffList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public QueueManager getQueueManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeFromQueue(ReferenceId target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void enqueuePatient(ReferenceId id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void enqueuePatientToIndex(ReferenceId id, int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isPatientInQueue(ReferenceId id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isPatientBeingServed(ReferenceId id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isDoctorOnDuty(ReferenceId id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void changePatientRefIdInQueue(ReferenceId idToEdit, ReferenceId editedId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Room> getConsultationRoomList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addRoom(Room room) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeRoom(Room room) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasRoom(Room room) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAppointmentSchedule(ReadOnlyAppointmentBook schedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAppointmentBook getAppointmentBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAppointment(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasExactAppointment(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteAppointment(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void scheduleAppointment(Event appointment) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void scheduleAppointments(List<Event> appointments) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAppointment(Event target, Event editedEvent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ListIterator<Event> getAppointmentsInConflict(Event toCheck) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getNumberOfAppointmentsInConflict(Event toCheck) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Event> getFilteredAppointmentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Event> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Boolean isListingAppointmentsOfSinglePatient() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Boolean isListingAppointmentsOfSingleStaff() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Boolean isMissedList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDutyShiftSchedule(ReadOnlyAppointmentBook schedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAppointmentBook getDutyShiftBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasDutyShift(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasExactDutyShift(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteDutyShifts(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteDutyShifts(List<Event> dutyShifts) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void scheduleDutyShift(Event dutyShift) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void scheduleDutyShift(List<Event> dutyShifts) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDutyShift(Event target, Event editedEvent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ListIterator<Event> getDutyShiftInConflict(Event toCheck) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getNumberOfDutyShiftInConflict(Event toCheck) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Event> getFilteredDutyShiftList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredDutyShiftList(Predicate<Event> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTabListing(OmniPanelTab tab) {
    }

    @Override
    public void bindTabListingCommand(Consumer<OmniPanelTab> tabConsumer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<ReferenceId> getQueueList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Person resolvePatient(ReferenceId person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Person resolveStaff(ReferenceId id) {
        throw new AssertionError("This method should not be called.");
    }

}
