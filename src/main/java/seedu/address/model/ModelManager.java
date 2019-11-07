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
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteTrainingCommand;
import seedu.address.logic.commands.EditCommand;
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
    private final Attendance attendance;
    private final Performance performance;
    private final FilteredList<Person> filteredPersons;
    private ReadOnlyAthletick readOnlyAthletick;
    private Person selectedPerson;


    /**
     * Initializes a ModelManager with the given athletick, performance, attendance and userPrefs.
     */
    public ModelManager(ReadOnlyAthletick athletick, ReadOnlyPerformance performance,
                        Attendance attendance, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(athletick, userPrefs);

        logger.fine("Initializing with athletick: " + athletick + " and user prefs " + userPrefs);

        this.athletick = new Athletick(athletick);
        this.userPrefs = new UserPrefs(userPrefs);
        this.performance = new Performance(performance);
        this.attendance = attendance;
        filteredPersons = new FilteredList<>(this.athletick.getPersonList());
    }

    public ModelManager() {
        this(new Athletick(), new Performance(), new Attendance(), new UserPrefs());
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
    public Command undo() {
        Command undoneCommand = HistoryManager.getCommands().pop();
        ReadOnlyAthletick undoneAthletick = HistoryManager.getAddressBooks().pop();
        HistoryManager.getUndoneCommands().push(undoneCommand);
        HistoryManager.getUndoneAddressBooks().push(undoneAthletick);
        //undo add/delete training command
        if (undoneCommand instanceof TrainingCommand || undoneCommand instanceof DeleteTrainingCommand) {
            List<Training> undoneTrainingList = this.getTrainingsDeepCopy(HistoryManager.getTrainingLists().pop());
            HistoryManager.getUndoneTrainingLists().push(undoneTrainingList);
            List<Training> afterUndoneTrainingList =
                this.getTrainingsDeepCopy(HistoryManager.getTrainingLists().peek());
            attendance.resetTrainingList(afterUndoneTrainingList);
        //undo normal addressbook commands
        } else if (undoneCommand instanceof EditCommand) {
            ReadOnlyAthletick afterUndoneState = HistoryManager.getAddressBooks().peek();
            athletick.resetData(afterUndoneState);
            List<Training> undoneTrainingList = this.getTrainingsDeepCopy(HistoryManager.getTrainingLists().pop());
            HistoryManager.getUndoneTrainingLists().push(undoneTrainingList);
            List<Training> afterUndoneTrainingList =
                this.getTrainingsDeepCopy(HistoryManager.getTrainingLists().peek());
            attendance.resetTrainingList(afterUndoneTrainingList);
        } else {
            ReadOnlyAthletick afterUndoneState = HistoryManager.getAddressBooks().peek();
            athletick.resetData(afterUndoneState);
        }
        return undoneCommand;
    }

    @Override
    public Command redo() {
        Command redoneCommand = HistoryManager.getUndoneCommands().pop();
        ReadOnlyAthletick redoneAthletick = HistoryManager.getUndoneAddressBooks().pop();
        HistoryManager.getCommands().push(redoneCommand);
        HistoryManager.getAddressBooks().push(redoneAthletick);
        if (redoneCommand instanceof TrainingCommand || redoneCommand instanceof DeleteTrainingCommand) {
            List<Training> redoneTrainingLists = getTrainingsDeepCopy(HistoryManager.getUndoneTrainingLists().pop());
            HistoryManager.getTrainingLists().push(redoneTrainingLists);
            attendance.resetTrainingList(getTrainingsDeepCopy(redoneTrainingLists));
        } else if (redoneCommand instanceof EditCommand) {
            List<Training> redoneTrainingLists = getTrainingsDeepCopy(HistoryManager.getUndoneTrainingLists().pop());
            HistoryManager.getTrainingLists().push(redoneTrainingLists);
            attendance.resetTrainingList(getTrainingsDeepCopy(redoneTrainingLists));
            athletick.resetData(redoneAthletick);
        } else {
            athletick.resetData(redoneAthletick);
        }
        return redoneCommand;
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

    //=========== Training & Attendance =======================================================================
    @Override
    public void addTraining(Training training) {
        this.attendance.addTraining(training);
    }

    @Override
    public void editPersonTrainingRecords(Person target, Person editedPerson) {
        this.attendance.editPersonTrainingRecords(target, editedPerson);
    }

    @Override
    public boolean hasTrainingOnDate(AthletickDate date) {
        return this.attendance.hasTrainingOnDate(date);
    }

    @Override
    public Training deleteTrainingOnDate(AthletickDate date) {
        return this.attendance.deleteTrainingOnDate(date);
    }

    @Override
    public List<AttendanceEntry> getTrainingAttendanceListOnDate(AthletickDate date) {
        return attendance.getTrainingAttendanceListOnDate(date);
    }

    @Override
    public List<AttendanceRateEntry> getAttendanceRateOfAll() {
        List<Person> allPeople = getAthletick().getPersonList();
        List<AttendanceRateEntry> attendanceRateEntries = new ArrayList<>();
        for (Person person : allPeople) {
            attendanceRateEntries.add(new AttendanceRateEntry(person,
                    attendance.getPersonAttendanceRateString(person)));
        }
        return attendanceRateEntries;
    }

    @Override
    public Attendance getAttendance() {
        return this.attendance;
    }

    @Override
    public void resetAttendance() {
        this.attendance.resetAttendance();
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
}
