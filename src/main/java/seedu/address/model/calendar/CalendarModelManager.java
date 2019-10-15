package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendar.person.Task;






/**
 * Represents the in-memory calendarModel of the address book data.
 */
public class CalendarModelManager implements CalendarModel {
    private static final Logger logger = LogsCenter.getLogger(CalendarModelManager.class);

    private final CalendarCalendarAddressBook calendarAddressBook;
    private final CalendarUserPrefs userPrefs;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a CalendarModelManager with the given calendarAddressBook and userPrefs.
     */
    public CalendarModelManager(ReadOnlyCalendarAddressBook addressBook, ReadOnlyCalendarUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.calendarAddressBook = new CalendarCalendarAddressBook(addressBook);
        this.userPrefs = new CalendarUserPrefs(userPrefs);
        filteredTasks = new FilteredList<>(this.calendarAddressBook.getPersonList());
    }

    public CalendarModelManager() {
        this(new CalendarCalendarAddressBook(), new CalendarUserPrefs());
    }

    //=========== CalendarUserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyCalendarUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyCalendarUserPrefs getUserPrefs() {
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

    //=========== CalendarAddressBook =========================================================================

    @Override
    public void setCalendarAddressBook(ReadOnlyCalendarAddressBook calendarAddressBook) {
        this.calendarAddressBook.resetData(calendarAddressBook);
    }

    @Override
    public ReadOnlyCalendarAddressBook getCalendarAddressBook() {
        return calendarAddressBook;
    }

    @Override
    public boolean hasPerson(Task task) {
        requireNonNull(task);
        return calendarAddressBook.hasPerson(task);
    }

    @Override
    public void deletePerson(Task target) {
        calendarAddressBook.removePerson(target);
    }

    @Override
    public void addPerson(Task task) {
        calendarAddressBook.addPerson(task);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        calendarAddressBook.setPerson(target, editedTask);
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Task> getFilteredPersonList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CalendarModelManager)) {
            return false;
        }

        // state check
        CalendarModelManager other = (CalendarModelManager) obj;
        return calendarAddressBook.equals(other.calendarAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredTasks.equals(other.filteredTasks);
    }

}
