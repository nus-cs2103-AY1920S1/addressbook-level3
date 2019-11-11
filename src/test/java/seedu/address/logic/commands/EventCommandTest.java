package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT;
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
import seedu.address.model.AttendanceRateEntry;
import seedu.address.model.Model;
import seedu.address.model.Performance;
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

public class EventCommandTest {

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventCommand(null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new Event(VALID_EVENT);

        CommandResult commandResult = new EventCommand(validEvent).execute(modelStub);

        assertEquals(String.format(EventCommand.MESSAGE_SUCCESS, VALID_EVENT), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event validEvent = new Event(VALID_EVENT);
        EventCommand eventCommand = new EventCommand(validEvent);
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        assertThrows(CommandException.class,
            String.format(EventCommand.MESSAGE_DUPLICATE_EVENT, VALID_EVENT), () -> eventCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Event butterly = new Event("butterly");
        Event freestyle = new Event("freestyle");
        EventCommand addButterflyCommand = new EventCommand(butterly);
        EventCommand addFreeStyleCommand = new EventCommand(freestyle);

        // same object -> returns true
        assertTrue(addButterflyCommand.equals(addButterflyCommand));

        // same values -> returns true
        EventCommand addButterflyCommandCopy = new EventCommand(butterly);
        assertTrue(addButterflyCommand.equals(addButterflyCommandCopy));

        // different types -> returns false
        assertFalse(addButterflyCommand.equals(1));

        // null -> returns false
        assertFalse(addButterflyCommand.equals(null));

        // different person -> returns false
        assertFalse(addButterflyCommand.equals(addFreeStyleCommand));
    }

    /**
     * A Model stub that contains a single event.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public ReadOnlyPerformance getPerformance() {
            return new Performance();
        }
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
        public String getPersonAttendanceRateString(Person person) {
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

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            return false;
        }

        @Override
        public Event getEvent(String eventName) {
            return null;
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPerformance getPerformance() {
            return null;
        }

        @Override
        public void addRecord(String eventName, Person person, Record record) {
        }

        @Override
        public void deleteRecord(String eventName, Person person, AthletickDate date) {
        }

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
}
