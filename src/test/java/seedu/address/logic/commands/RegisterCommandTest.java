package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.IncidentManager;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyIncidentManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.Session;
import seedu.address.model.incident.Incident;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.testutil.PersonBuilder;

public class RegisterCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RegisterCommand(null));
    }

    //@@author madanalogy
    @Test
    void execute_nonAdminAddTags_throwsCommandException() {
        ModelStubWithSession modelStub = new ModelStubWithSession();
        Person validPerson = new PersonBuilder().withTags("admin").build();
        RegisterCommand registerCommand = new RegisterCommand(validPerson);
        modelStub.setSession(new PersonBuilder().build());

        assertThrows(CommandException.class, Messages.MESSAGE_ACCESS_ADMIN, () -> registerCommand.execute(modelStub));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new RegisterCommand(validPerson).execute(modelStub);

        assertEquals(String.format(RegisterCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        RegisterCommand registerCommand = new RegisterCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class,
                RegisterCommand.MESSAGE_DUPLICATE_PERSON, () -> registerCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").withUsername("alice").build();
        Person bob = new PersonBuilder().withName("Bob").withUsername("bob").build();
        RegisterCommand addAliceCommand = new RegisterCommand(alice);
        RegisterCommand addBobCommand = new RegisterCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        RegisterCommand addAliceCommandCopy = new RegisterCommand(alice);
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
        public void setSession(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getLoggedInPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getLoginTime() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isLoggedIn() {
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
        public Path getIncidentManagerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIncidentManagerFilePath(Path incidentManagerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIncidentManager(ReadOnlyIncidentManager newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyIncidentManager getIncidentManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
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
        public boolean hasIncident(Incident incident) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addIncident(Incident incident) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeIncident(Incident incident) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredIncidentList(Predicate<Incident> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasVehicle(Vehicle vehicle) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasVNum(String vNum) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVehicle(Vehicle target, Vehicle editedVehicle) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addVehicle(Vehicle toAdd) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void deleteVehicle(Vehicle toDelete) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateFilteredVehicleList(Predicate<Vehicle> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Incident> getFilteredIncidentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIncident(Incident target, Incident editedIncident) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canLoggedInPersonModifyIncidentStatus(Incident toSubmit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Vehicle> getFilteredVehicleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean ifAnyIncidentsSatisfyPredicate(Predicate<Incident> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyIncidentManager getIncidentManager() {
            return new IncidentManager();
        }
    }

    //@@author madanalogy
    /**
     * A Model stub that allows for {@code Session} management while accepting any added person.
     */
    private class ModelStubWithSession extends ModelStubAcceptingPersonAdded {
        private Session session;

        ModelStubWithSession() {
            this.session = new Session(null);
        }

        @Override
        public void setSession(Person person) {
            session = new Session(person);
        }

        @Override
        public Person getLoggedInPerson() {
            return session.getLoggedInPerson();
        }

        @Override
        public String getLoginTime() {
            return session.getLoginTime();
        }

        @Override
        public boolean isLoggedIn() {
            return session.getLoggedInPerson() != null;
        }
    }
}
