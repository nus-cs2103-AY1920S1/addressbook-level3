package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.distinctdate.DistinctDate;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Employee> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns List of All Employees.
     */
    ObservableList<Employee> getFullListEmployees();

    /**
     * Returns true if a employee with the same identity as {@code employee} exists in the address book.
     */
    boolean hasEmployee(Employee employee);

    /**
     * Deletes the given employee.
     * The employee must exist in the address book.
     */
    void deleteEmployee(Employee target);

    /**
     * Adds the given employee.
     * {@code employee} must not already exist in the address book.
     */
    void addEmployee(Employee employee);

    /**
     * Replaces the given employee {@code target} with {@code editedEmployee}.
     * {@code target} must exist in the address book.
     * The employee identity of {@code editedEmployee} must not be the same
     * as another existing employee in the address book.
     */
    void setEmployee(Employee target, Employee editedEmployee);

    /**
     * Returns an unmodifiable view of the filtered employee list
     */
    ObservableList<Employee> getFilteredEmployeeList();

    /**
     * Updates the filter of the filtered employee list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEmployeeList(Predicate<Employee> predicate);


    /**
     * Returns the user prefs' event book file path.
     */
    Path getEventBookFilePath();

    /**
     * Sets the user prefs' event book file path.
     */
    void setEventBookFilePath(Path eventBookFilePath);

    /**
     * Replaces event book data with the data in {@code eventBook}.
     */
    void setEventBook(ReadOnlyEventBook eventBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyEventBook getEventBook();

    /**
     * Returns List of All Employees.
     */
    ObservableList<Event> getFullListEvents();

    /**
     * Returns true if a event with the same identity as {@code event} exists in the event book.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the event book.
     */
    void deleteEvent(Event target);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the event book.
     */
    void addEvent(Event event);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the event book.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the event book.
     */
    void setEvent(Event target, Event editedEvent);

    /**
     * Returns an unmodifiable view of the filtered event list
     */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered Event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Returns an unmodifiable view of the filtered event list
     */
    ObservableList<Event> getFilteredScheduledEventList();

    /**
     * Updates the filter of the filtered Event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredScheduledEventList(Predicate<Event> predicate);

    ObservableList<DistinctDate> getEmployeeDistinctDatesList();

    /**
     * Returns an unmodifiable view of the DistinctDate list
     */
    ObservableList<DistinctDate> getEventDistinctDatesList();

    /**
     * Updates the Employees DistinctDatesList by taking in a new List of DistinctDate Objects
     *
     * @param list
     */
    void updateEmployeeDistinctDateList(List<DistinctDate> list);

    /**
     * Updates the Event DistinctDatesList by taking in a new List of DistinctDate Objects
     *
     * @param list of DistinctDate objects to replace the old list.
     */
    void updateEventDistinctDatesList(List<DistinctDate> list);
}
