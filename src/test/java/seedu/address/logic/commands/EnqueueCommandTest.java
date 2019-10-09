package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.events.Event;
import seedu.address.model.person.Person;
import seedu.address.model.queue.QueueManager;
import seedu.address.model.queue.Room;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;
import seedu.address.testutil.PersonBuilder;

public class EnqueueCommandTest {
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EnqueueCommand(null));
    }

    @Test
    public void equals() {
        ReferenceId alice = new PersonBuilder().withId("2322").withName("Alice").build().getReferenceId();
        ReferenceId bob = new PersonBuilder().withId("32323").withName("Bob").build().getReferenceId();
        EnqueueCommand addAliceCommand = new EnqueueCommand(alice);
        EnqueueCommand addBobCommand = new EnqueueCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        EnqueueCommand addAliceCommandCopy = new EnqueueCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAppointmentBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointmentBookFilePath(Path appointmentBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasExactPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
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
        public void removeFromQueue(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void enqueuePatient(ReferenceId id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isPatientInQueue(ReferenceId id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void serveNextPatient(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRoom(ReferenceId id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeRoom(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSchedule(ReadOnlyAppointmentBook schedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAppointmentBook getAppointmentBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasExactEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ReferenceId> getFilteredReferenceIdList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReferenceIdList(Predicate<ReferenceId> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Room> getFilteredRoomList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRoomList(Predicate<Room> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person resolve(ReferenceId id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(ReferenceId id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single patient.
     */
    private class ModelStubWithPatient extends ModelStub {
        private final ReferenceId id;

        ModelStubWithPatient(ReferenceId patient) {
            requireNonNull(patient);
            this.id = patient;
        }

        @Override
        public boolean isPatientInQueue(ReferenceId patient) {
            requireNonNull(patient);
            return this.id.equals(patient);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPatientAdded extends ModelStub {
        final ArrayList<ReferenceId> ids = new ArrayList<>();

        @Override
        public boolean isPatientInQueue(ReferenceId id) {
            requireNonNull(id);
            return ids.stream().anyMatch(id::equals);
        }

        @Override
        public void enqueuePatient(ReferenceId id) {
            requireNonNull(id);
            ids.add(id);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
