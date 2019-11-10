package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteRecordCommand;
import seedu.address.logic.commands.DeleteTrainingCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EventCommand;
import seedu.address.logic.commands.PerformanceCommand;
import seedu.address.logic.commands.TrainingCommand;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.history.HistoryManager;
import seedu.address.model.performance.CalendarCompatibleRecord;
import seedu.address.model.performance.Event;
import seedu.address.model.performance.Record;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.training.AttendanceEntry;
import seedu.address.model.training.Training;

/**
 * Represents the in-memory model of Athletick data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Athletick athletick;
    private final UserPrefs userPrefs;
    private final TrainingManager trainingManager;
    private final Performance performance;
    private final FilteredList<Person> filteredPersons;
    private final HistoryManager history;
    private ReadOnlyAthletick readOnlyAthletick;
    private Person selectedPerson;


    /**
     * Initializes a ModelManager with the given athletick, performance, attendance and userPrefs.
     */
    public ModelManager(ReadOnlyAthletick athletick, ReadOnlyPerformance performance,
                        TrainingManager trainingManager, ReadOnlyUserPrefs userPrefs, HistoryManager history) {
        super();
        requireAllNonNull(athletick, userPrefs);

        logger.fine("Initializing with athletick: " + athletick + " and user prefs " + userPrefs);

        this.athletick = new Athletick(athletick);
        this.userPrefs = new UserPrefs(userPrefs);
        this.performance = new Performance(performance);
        this.trainingManager = trainingManager;
        filteredPersons = new FilteredList<>(this.athletick.getPersonList());
        this.history = history;
        this.history.init(this);
    }

    public ModelManager() {
        this(new Athletick(), new Performance(), new TrainingManager(), new UserPrefs(), new HistoryManager());
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

    @Override
    public Path getAthletickFilePath() {
        return userPrefs.getAthletickFilePath();
    }

    @Override
    public void setAthletickFilePath(Path athletickFilePath) {
        requireNonNull(athletickFilePath);
        userPrefs.setAthletickFilePath(athletickFilePath);
    }

    //=========== Athletick ========================================================================

    @Override
    public void setAthletick(ReadOnlyAthletick athletick) {
        this.athletick.resetData(athletick);
    }

    @Override
    public ReadOnlyAthletick getAthletick() {
        return athletick;
    }
    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return athletick.hasPerson(person);
    }
    @Override
    public void deletePerson(Person target) {
        athletick.removePerson(target);
    }
    @Override
    public void addPerson(Person person) {
        athletick.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        athletick.setPerson(target, editedPerson);
    }
    @Override
    public Person selectPerson() {
        return selectedPerson;
    }

    public void storePerson(Person person) {
        selectedPerson = person;
    }

    public void sortAthletickByName() {
        this.athletick.sortByName();
    }

    //=========== HistoryManager ========================================================================
    @Override
    public HistoryManager getHistory() {
        return this.history;
    }

    @Override
    public ReadOnlyAthletick getAthletickDeepCopy() {
        UniquePersonList persons = athletick.getPersons();
        Athletick deepCopy = new Athletick();
        deepCopy.getPersons().setPersons(persons);
        return deepCopy;
    }
    @Override
    public List<Training> getTrainingsDeepCopy(List<Training> trainingsList) {
        List<Training> trainingsDeepCopy = new ArrayList<>();
        for (Training training: trainingsList) {
            Training trainingDeepCopy = new Training(training.getDate(),
                deepCopyHashMap(training.getTrainingAttendance()));
            trainingsDeepCopy.add(trainingDeepCopy);
        }
        return trainingsDeepCopy;
    }
    @Override
    public HashMap<Person, Boolean> deepCopyHashMap(HashMap<Person, Boolean> mapToCopy) {
        HashMap<Person, Boolean> deepCopy = new HashMap<>();
        for (Map.Entry<Person, Boolean> entry: mapToCopy.entrySet()) {
            deepCopy.put(entry.getKey(), entry.getValue());
        }
        return deepCopy;
    }
    @Override
    public ReadOnlyPerformance getPerformanceDeepCopy(ReadOnlyPerformance originalPerformance) {
        List<Event> originalEvents = originalPerformance.getPerformance();
        List<Event> eventsCopy = getEventsDeepCopy(originalEvents);
        Performance performanceCopy = new Performance();
        performanceCopy.setEvents(eventsCopy);
        return performanceCopy;
    }
    @Override
    public List<Event> getEventsDeepCopy(List<Event> originalEvents) {
        List<Event> eventsCopy = new ArrayList<>();
        for (Event originalEvent: originalEvents) {
            eventsCopy.add(getEventDeepCopy(originalEvent));
        }
        return eventsCopy;
    }
    @Override
    public Event getEventDeepCopy(Event originalEvent) {
        HashMap<Person, List<Record>> hashMapCopy = new HashMap<>();
        for (Map.Entry<Person, List<Record>> entry: originalEvent.getRecords().entrySet()) {
            hashMapCopy.put(entry.getKey(),
                getRecordsDeepCopy(entry.getValue()));
        }
        Event eventCopy = new Event(originalEvent.getName(), hashMapCopy);
        return eventCopy;
    }
    @Override
    public List<Record> getRecordsDeepCopy(List<Record> originalRecords) {
        List<Record> recordsCopy = new ArrayList<>();
        for (Record record: originalRecords) {
            recordsCopy.add(getRecordDeepCopy(record));
        }
        return recordsCopy;
    }
    @Override
    public Record getRecordDeepCopy(Record originalRecord) {
        Record recordCopy = new Record(originalRecord.getDate(), originalRecord.getTiming());
        return recordCopy;
    }
    @Override
    public boolean commandUnderTraining(Command command) {
        return command instanceof TrainingCommand || command instanceof DeleteTrainingCommand;
    }
    @Override
    public boolean commandUnderPerformance(Command command) {
        return command instanceof EventCommand || command instanceof PerformanceCommand
            || command instanceof DeleteEventCommand || command instanceof DeleteRecordCommand;
    }
    @Override
    public Command undo() {
        Command undoneCommand = this.history.popLatestCommand();
        ReadOnlyAthletick undoneAthletick = this.history.popLatestAthletick();
        this.history.pushUndoneCommand(undoneCommand);
        this.history.pushUndoneAthletick(undoneAthletick);
        if (commandUnderTraining(undoneCommand)) {
            this.history.undoTrainingStack(trainingManager, this);
        } else if (commandUnderPerformance(undoneCommand)) {
            this.history.undoPerformanceStack(performance, this);
        } else if (undoneCommand instanceof EditCommand || undoneCommand instanceof ClearCommand) {
            this.history.undoAthletickStack(athletick);
            this.history.undoTrainingStack(trainingManager, this);
            this.history.undoPerformanceStack(performance, this);
        } else {
            this.history.undoAthletickStack(athletick);
        }
        return undoneCommand;
    }
    @Override
    public Command redo() {
        Command redoneCommand = this.history.popLatestUndoneCommand();
        ReadOnlyAthletick redoneAthletick = this.history.popLatestUndoneAthletick();
        this.history.getCommands().push(redoneCommand);
        this.history.getAddressBooks().push(redoneAthletick);
        if (commandUnderTraining(redoneCommand)) {
            this.history.redoTrainingStack(trainingManager, this);
        } else if (commandUnderPerformance(redoneCommand)) {
            this.history.redoPerformanceStack(performance, this);
        } else if (redoneCommand instanceof EditCommand || redoneCommand instanceof ClearCommand) {
            this.history.redoTrainingStack(trainingManager, this);
            this.history.redoPerformanceStack(performance, this);
            athletick.resetData(redoneAthletick);
        } else {
            athletick.resetData(redoneAthletick);
        }
        return redoneCommand;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

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
        return athletick.equals(other.athletick)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== Training  =======================================================================
    @Override
    public void addTraining(Training training) {
        this.trainingManager.addTraining(training);
    }

    @Override
    public void editPersonTrainingRecords(Person target, Person editedPerson) {
        this.trainingManager.editPersonTrainingRecords(target, editedPerson);
    }

    @Override
    public boolean hasTrainingOn(AthletickDate date) {
        return this.trainingManager.hasTrainingOnDate(date);
    }

    @Override
    public Training deleteTrainingOnDate(AthletickDate date) {
        return this.trainingManager.deleteTrainingOnDate(date);
    }

    @Override
    public List<AttendanceEntry> getTrainingAttendanceListOnDate(AthletickDate date) {
        return trainingManager.getTrainingAttendanceListOnDate(date);
    }

    @Override
    public List<AttendanceRateEntry> getAttendanceRateOfAll() {
        List<Person> allPeople = getAthletick().getPersonList();
        List<AttendanceRateEntry> attendanceRateEntries = new ArrayList<>();
        for (Person person : allPeople) {
            attendanceRateEntries.add(new AttendanceRateEntry(person,
                    trainingManager.getPersonAttendanceRateString(person)));
        }
        return attendanceRateEntries;
    }

    @Override
    public TrainingManager getTrainingManager() {
        return this.trainingManager;
    }

    @Override
    public void resetTrainingManager() {
        this.trainingManager.resetTrainingManager();
    }

    //=========== Performance =================================================================================

    @Override
    public void setPerformance(ReadOnlyPerformance performance) {
        this.performance.resetData(performance);
    }

    @Override
    public void addEvent(Event event) {
        performance.addEvent(event);
    }

    @Override
    public boolean hasEvent(Event event) {
        return performance.hasEvent(event);
    }

    @Override
    public Event getEvent(String eventName) {
        return performance.getEvent(eventName);
    }

    @Override
    public void deleteEvent(Event target) {
        performance.removeEvent(target);
    }

    @Override
    public ReadOnlyPerformance getPerformance() {
        return performance;
    }

    @Override
    public void addRecord(String eventName, Person person, Record record) {
        performance.addRecord(eventName, person, record);
    }

    @Override
    public void deleteRecord(String eventName, Person person, AthletickDate date) {
        performance.deleteRecord(eventName, person, date);
    }

    @Override
    public HashMap<Event, List<CalendarCompatibleRecord>> getCalendarCompatiblePerformance(AthletickDate date) {
        return performance.getCalendarCompatiblePerformance(date);
    }

    @Override
    public boolean hasPerformanceOn(AthletickDate date) {
        return performance.hasPerformanceOn(date);
    }

    @Override
    public ArrayList<Event> getAthleteEvents(Person athlete) {
        return performance.getAthleteEvent(athlete);
    }

    @Override
    public void editPersonPerformanceRecords(Person target, Person editedPerson) {
        performance.editPersonPerformanceRecords(target, editedPerson);
    }

}
