package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.Command;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.history.HistoryManager;
import seedu.address.model.performance.CalendarCompatibleRecord;
import seedu.address.model.performance.Event;
import seedu.address.model.performance.Record;
import seedu.address.model.person.Person;
import seedu.address.model.training.AttendanceEntry;
import seedu.address.model.training.Training;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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

    /**
     * Returns the user prefs' Athletick file path.
     */
    Path getAthletickFilePath();

    /**
     * Sets the user prefs' Athletick file path.
     */
    void setAthletickFilePath(Path athletickFilePath);

    /**
     * Replaces Athletick data with the data in {@code athletick}.
     */
    void setAthletick(ReadOnlyAthletick athletick);
    /**
     * Returns Athletick
     */
    ReadOnlyAthletick getAthletick();
    /**
     * Returns HistoryManager
     */
    HistoryManager getHistory();
    /**
     * Returns deep copy of Athletick
     */
    ReadOnlyAthletick getAthletickDeepCopy();
    /**
     * Returns deep copy of Trainings under Attendance
     */
    List<Training> getTrainingsDeepCopy(List<Training> trainingsList);
    /**
     * Returns deep copy of HashMap under Training
     */
    HashMap<Person, Boolean> deepCopyHashMap(HashMap<Person, Boolean> mapToCopy);
    /**
     * Returns deep copy of Performance
     */
    ReadOnlyPerformance getPerformanceDeepCopy(ReadOnlyPerformance originalPerformance);
    /**
     * Returns deep copy of Events under Performance
     */
    List<Event> getEventsDeepCopy(List<Event> originalEvents);
    /**
     * Returns deep copy of HashMap under Event
     */
    Event getEventDeepCopy(Event originalEvent);
    /**
     * Returns deep copy of List of Record under Event
     */
    List<Record> getRecordsDeepCopy(List<Record> originalRecords);
    /**
     * Returns deep copy of Record under Event
     */
    Record getRecordDeepCopy(Record originalRecord);
    /**
     * Returns whether command is instanceof TrainingCommand or DeleteTrainingCommand
     */
    boolean commandUnderTraining(Command command);
    /**
     * Returns whether command is instanceof EventCommand/PerformanceCommand/DeleteEventCommand or
     * DeleteRecordCommand
     */
    boolean commandUnderPerformance(Command command);
    /**
     * Returns Command that is being undone
     */
    Command undo();
    /**
     * Returns Command that is being redone
     */
    Command redo();
    /**
     * Returns true if a person with the same identity as {@code person} exists in Athletick.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in Athletick.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in Athletick.
     */
    void addPerson(Person person);

    Person selectPerson();

    void storePerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in Athletick.
     * The person identity of {@code editedPerson} must not be the same as another existing
     * person in Athletick.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Reorders Athletick in alphabetical order according to person's name.
     */
    void sortAthletickByName();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Adds {@code training} to the TrainingManager class.
     */
    void addTraining(Training training);

    /**
     * Replaces all occurrences of person at {@code target} with {@code editedPerson} in training records.
     */
    void editPersonTrainingRecords(Person target, Person editedPerson);

    /**
     * Removes training on {@code date} from the TrainingManager.
     */
    Training deleteTrainingOnDate(AthletickDate date);

    /**
     * Gets a list of AttendanceEntry on {@code date}, where each entry indicates whether a person was present.
     * @param date Date of training.
     * @return List of AttendanceEntry, where each entry indicates whether a person was present for training on date.
     */
    List<AttendanceEntry> getTrainingAttendanceListOnDate(AthletickDate date);

    /**
     * Returns String representation of {@code person} attendance rate.
     */
    String getPersonAttendanceRateString(Person person);

    /**
     * Returns a list of AttendanceRateEntry, where each entry indicates the attendance rate of a person.
     */
    List<AttendanceRateEntry> getAttendanceRateOfAll();

    /**
     * Returns the Training Manager.
     */
    TrainingManager getTrainingManager();

    /**
     * Resets all data in the TrainingManager.
     */
    void resetTrainingManager();

    /**
     * Checks with Attendance if there was a Training on {@code date}.
     * @param date Date of training.
     * @return Boolean indicating if there was a training on {@code date}.
     */
    boolean hasTrainingOn(AthletickDate date);

    /**
     * Replaces performance data with the data in {@code performance}.
     */
    void setPerformance(ReadOnlyPerformance performance);

    void addEvent(Event event);

    boolean hasEvent(Event event);

    Event getEvent(String eventName);

    /**
     * Deletes the given event.
     * The event must exist in performance.
     */
    void deleteEvent(Event target);

    ReadOnlyPerformance getPerformance();

    void addRecord(String eventName, Person person, Record record);

    void deleteRecord(String eventName, Person person, AthletickDate date);

    HashMap<Event, List<CalendarCompatibleRecord>> getCalendarCompatiblePerformance(AthletickDate date);

    boolean hasPerformanceOn(AthletickDate date);

    ArrayList<Event> getAthleteEvents(Person athlete);

    void editPersonPerformanceRecords(Person target, Person editedPerson);

}
