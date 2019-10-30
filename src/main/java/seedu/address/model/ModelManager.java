package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.TrainingCommand;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.history.HistoryManager;
import seedu.address.model.performance.CalendarCompatibleRecord;
import seedu.address.model.performance.Event;
import seedu.address.model.performance.Record;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.training.Training;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final Attendance attendance;
    private final Performance performance;
    private final FilteredList<Person> filteredPersons;
    private ReadOnlyAddressBook readOnlyAddressBook;
    private Person selectedPerson;
    private HistoryManager history = new HistoryManager();


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyPerformance performance,
                        Attendance attendance, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.performance = new Performance(performance);
        this.attendance = attendance;
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new Performance(), new Attendance(), new UserPrefs());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }
    @Override
    public ReadOnlyAddressBook getAddressBookDeepCopy() {
        UniquePersonList persons = addressBook.getPersons();
        AddressBook deepCopy = new AddressBook();
        deepCopy.getPersons().setPersons(persons);
        return deepCopy;
    }
    @Override
    public void undo() {
        Command undoneCommand = HistoryManager.getCommands().pop();
        ReadOnlyAddressBook undoneAddressBooks = HistoryManager.getAddressBooks().pop();
        HistoryManager.getUndoneCommands().push(undoneCommand);
        HistoryManager.getUndoneAddressBooks().push(undoneAddressBooks);
        if (undoneCommand instanceof TrainingCommand) {
            int attendanceListSize = this.attendance.getTrainings().size();
            int lastIndex = attendanceListSize - 1;
            Training undoneTraining = this.attendance.getTrainings().remove(lastIndex);
            HistoryManager.getUndoneTrainingLists().push(undoneTraining);
        } else {
            ReadOnlyAddressBook afterUndoneState = HistoryManager.getAddressBooks().peek();
            addressBook.resetData(afterUndoneState);
        }
    }
    @Override
    public void redo() {
        Command redoneCommand = HistoryManager.getUndoneCommands().pop();
        ReadOnlyAddressBook redoneAddressBook = HistoryManager.getUndoneAddressBooks().pop();
        HistoryManager.getCommands().push(redoneCommand);
        HistoryManager.getAddressBooks().push(redoneAddressBook);
        if (redoneCommand instanceof TrainingCommand) {
            Training redoneTraining = HistoryManager.getUndoneTrainingLists().pop();
            this.attendance.getTrainings().add(redoneTraining);
        } else {
            addressBook.resetData(redoneAddressBook);
        }
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public Person selectPerson() {
        return selectedPerson;
    }

    public void storePerson(Person person) {
        selectedPerson = person;
    }

    public void sortAddressBookByName() {
        this.addressBook.sortByName();
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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== Training =================================================================================
    @Override
    public Training getTrainingOnDate(AthletickDate date) {
        return attendance.getTrainingOnDate(date);
    }

    //=========== Attendance =================================================================================

    @Override
    public Attendance getAttendance() {
        return this.attendance;
    }

    @Override
    public boolean hasTraining(AthletickDate date) {
        return this.attendance.hasTraining(date);
    }

    @Override
    public void addTraining(Training training) {
        this.attendance.addTraining(training);
    }

    //=========== Performance =================================================================================

    @Override
    public void addEvent(Event event) {
        performance.addEvent(event);
    }

    @Override
    public boolean hasEvent(Event event) {
        return performance.hasEvent(event);
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
    public HashMap<Event, List<CalendarCompatibleRecord>> getCalendarCompatiblePerformance(AthletickDate date) {
        return performance.getCalendarCompatiblePerformance(date);
    }

    @Override
    public boolean hasPerformanceOn(AthletickDate date) {
        return performance.hasPerformanceOn(date);
    }
}
