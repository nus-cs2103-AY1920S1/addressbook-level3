package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Slot;

/**
 * Represents the in-memory model of the schedule table data.
 */
public class ModelManager implements Model {
    public static final Schedule EMPTY_SCHEDULE = new Schedule("", new LinkedList<>());
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final List<Schedule> schedulesList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        List<Schedule> schedulesList) {
        super();
        requireAllNonNull(addressBook, userPrefs, schedulesList);

        logger.fine("Initializing with list of schedules: " + schedulesList + " and user prefs " + userPrefs);

        // TODO: Delete these later
        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

        this.schedulesList = cloneSchedulesList(schedulesList);
        this.userPrefs = new UserPrefs(userPrefs);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new LinkedList<>());
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

    //=========== Schedule ================================================================================
    /**
     * Replaces schedule data with the data in {@code schedule}.
     */
    @Override
    public void setSchedulesList(LinkedList<Schedule> schedulesList) {
        schedulesList.clear();
        schedulesList.addAll(schedulesList);
    }

    /** Returns the schedulesList **/
    @Override
    public List<Schedule> getSchedulesList() {
        return schedulesList;
    }

    /**
     * Returns a list of observable list of the schedules.
     */
    @Override
    public List<ObservableList<ObservableList<String>>> getObservableLists() {
        List<ObservableList<ObservableList<String>>> observableLists = new LinkedList<>();
        for (Schedule schedule : schedulesList) {
            observableLists.add(schedule.getObservableList());
        }
        return observableLists;
    }

    /**
     * Returns a list of interview slots assigned to the interviewee with the {@code intervieweeName}.
     */
    @Override
    public List<Slot> getInterviewSlots(String intervieweeName) {
        List<Slot> slots = new LinkedList<>();
        for (Schedule schedule : schedulesList) {
            slots.addAll(schedule.getInterviewSlots(intervieweeName));
        }
        return slots;
    }

    /**
     * Returns the date of the first schedule in which the interviewer exists in, otherwise return empty string.
     */
    @Override
    public String hasInterviewer(Interviewer interviewer) {
        String date = "";
        for (Schedule schedule : schedulesList) {
            if (schedule.hasInterviewer(interviewer)) {
                date = schedule.getDate();
                break;
            }
        }
        return date;
    }
    /**
     * Adds the given interviewer to schedule(s) in which the interviewer's availability fall.
     * If the interviewer's availability does not fall within any of the schedule, then the interviewer will not
     * be added into any of the schedule.
     */
    @Override
    public void addInterviewer(Interviewer interviewer) {
        for (Schedule schedule : schedulesList) {
            schedule.addInterviewer(interviewer);
        }
    }

    /**
     * Returns the deep copy of the schedules list given.
     *
     * @param schedulesList the list of schedules to be copied.
     * @return the deep copy of the schedules list given.
     */
    private static List<Schedule> cloneSchedulesList(List<Schedule> schedulesList) {
        List<Schedule> listClone = new LinkedList<>();
        for (Schedule schedule : schedulesList) {
            listClone.add(Schedule.cloneSchedule(schedule));
        }
        return listClone;
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
}
