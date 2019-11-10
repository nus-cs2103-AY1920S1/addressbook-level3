package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Athletick;
import seedu.address.model.AttendanceRateEntry;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAthletick;
import seedu.address.model.ReadOnlyPerformance;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.TrainingManager;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.history.HistoryManager;
import seedu.address.model.performance.CalendarCompatibleRecord;
import seedu.address.model.performance.Event;
import seedu.address.model.performance.Record;
import seedu.address.model.person.Person;
import seedu.address.model.training.AttendanceEntry;
import seedu.address.model.training.Training;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
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
        public Path getAthletickFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAthletickFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person selectPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void storePerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAthletick(ReadOnlyAthletick newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAthletick getAthletick() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public HistoryManager getHistory() {
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
        public void sortAthletickByName() {
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
        public void addTraining(Training training) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editPersonTrainingRecords(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Training deleteTrainingOnDate(AthletickDate date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<AttendanceEntry> getTrainingAttendanceListOnDate(AthletickDate date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<AttendanceRateEntry> getAttendanceRateOfAll() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAthletick getAthletickDeepCopy() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public List<Training> getTrainingsDeepCopy(List<Training> trainingsList) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public HashMap<Person, Boolean> deepCopyHashMap(HashMap<Person, Boolean> mapToCopy) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyPerformance getPerformanceDeepCopy(ReadOnlyPerformance originalPerformance) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public List<Event> getEventsDeepCopy(List<Event> originalEvents) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Event getEventDeepCopy(Event originalEvent) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public List<Record> getRecordsDeepCopy(List<Record> originalRecords) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Record getRecordDeepCopy(Record originalRecord) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean commandUnderTraining(Command command) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean commandUnderPerformance(Command command) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Command undo() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Command redo() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public TrainingManager getTrainingManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetTrainingManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTrainingOn(AthletickDate training) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerformance(ReadOnlyPerformance performance) {
            throw new AssertionError("This method should not be called.");
        }

        public void addEvent(Event event) { }

        @Override
        public boolean hasEvent(Event event) {
            return false;
        }

        @Override
        public Event getEvent(String eventName) {
            return null;
        }

        @Override
        public void deleteEvent(Event target) { }

        @Override
        public ReadOnlyPerformance getPerformance() {
            return null;
        }

        @Override
        public void addRecord(String eventName, Person person, Record record) { }

        @Override
        public void deleteRecord(String eventName, Person person, AthletickDate date) { }

        @Override
        public HashMap<Event, List<CalendarCompatibleRecord>> getCalendarCompatiblePerformance(AthletickDate date) {
            return null;
        }

        @Override
        public boolean hasPerformanceOn(AthletickDate date) {
            return false;
        }

        @Override
        public ArrayList<Event> getAthleteEvents(Person person) {
            return null;
        }

        @Override
        public void editPersonPerformanceRecords(Person target, Person editedPerson) { }
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
        public ReadOnlyAthletick getAthletick() {
            return new Athletick();
        }
    }

}
