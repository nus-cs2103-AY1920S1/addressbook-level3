package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendar.task.Task;

/**
 * Represents the in-memory calendarModel of the address book data.
 */
public class CalendarModelManager implements CalendarModel {
    private static final Logger logger = LogsCenter.getLogger(CalendarModelManager.class);

    private final CalendarAddressBook calendarAddressBook;
    private final CalendarUserPrefs userPrefs;
    private FilteredList<Task> filteredTasks;
    private FilteredList<Task> filteredTasksByTimeAdded;
    private FilteredList<Task> filteredTasksByDeadline;

    private boolean isDeadlineSorted = false;

    /**
     * Initializes a CalendarModelManager with the given calendarAddressBook and userPrefs.
     */
    public CalendarModelManager(ReadOnlyCalendarAddressBook addressBook, ReadOnlyCalendarUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.calendarAddressBook = new CalendarAddressBook(addressBook);
        this.userPrefs = new CalendarUserPrefs(userPrefs);
        filteredTasks = new FilteredList<>(this.calendarAddressBook.getPersonList());

        filteredTasksByTimeAdded = new FilteredList<>(this.calendarAddressBook.getPersonList());

        filteredTasksByDeadline = new FilteredList<>(getSortedListByTime());
    }

    public CalendarModelManager() {
        this(new CalendarAddressBook(), new CalendarUserPrefs());
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
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return calendarAddressBook.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        calendarAddressBook.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        calendarAddressBook.addTask(task);
        calendarAddressBook.setTasks(filteredTasks);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        calendarAddressBook.setTask(target, editedTask);

        if (isDeadlineSorted) {
            filteredTasksByDeadline = new FilteredList<>(getSortedListByTime());
            filteredTasks = filteredTasksByDeadline;
            calendarAddressBook.setTasks(filteredTasks);
        }



    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
        filteredTasksByTimeAdded.setPredicate(predicate);
        filteredTasksByDeadline.setPredicate(predicate);
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

    @Override
    public void switchSortType(String sortType) {
        if (sortType.equals("deadline")) {
            isDeadlineSorted = true;
            filteredTasks = filteredTasksByDeadline;
        } else if (sortType.equals("timeadded")) {
            isDeadlineSorted = false;
            filteredTasks = filteredTasksByTimeAdded;
        }
        calendarAddressBook.setTasks(filteredTasks);

        updateFilteredTaskList(PREDICATE_SHOW_ALL_PERSONS);
    }

    private SortedList<Task> getSortedListByTime() {
        return new SortedList<>(this.calendarAddressBook.getPersonList(),
            new Comparator<Task>() {
                @Override
                public int compare(Task x, Task y) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date dateX = formatter.parse(x.getTaskDeadline().getValue());
                        Date dateY = formatter.parse(y.getTaskDeadline().getValue());
                        return dateX.compareTo(dateY);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
    }

}
