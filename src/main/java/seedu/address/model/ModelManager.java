package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.processor.DistinctDatesProcessor;
import seedu.address.model.distinctdate.DistinctDate;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Employee> filteredEmployees;
    private final EventBook eventBook;
    private final FilteredList<Event> filteredEvents;
    private final FilteredList<Event> filteredScheduledEvents;
    private ObservableList<DistinctDate> employeeDistinctDateList;
    private ObservableList<DistinctDate> eventDistinctDatesList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyEventBook eventBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, eventBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " Initializing with event book:"
                + eventBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.eventBook = new EventBook(eventBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredEmployees = new FilteredList<>(this.addressBook.getEmployeeList());
        filteredEvents = new FilteredList<>(this.eventBook.getEventList());
        filteredScheduledEvents = new FilteredList<>(this.eventBook.getEventList());
        employeeDistinctDateList = FXCollections
                .observableList(DistinctDatesProcessor.generateAllDistinctDateList(this));
        eventDistinctDatesList = FXCollections
                .observableList(DistinctDatesProcessor.generateAllDistinctDateList(this));
    }

    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        this(addressBook, new EventBook(), userPrefs);
    }

    public ModelManager() {
        this(new AddressBook(), new EventBook(), new UserPrefs());
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
    public Path getEventBookFilePath() {
        return userPrefs.getEventBookFilePath();
    }

    @Override
    public void setEventBookFilePath(Path eventBookFilePath) {
        requireNonNull(eventBookFilePath);
        userPrefs.setEventBookFilePath(eventBookFilePath);
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
    public ObservableList<Employee> getFullListEmployees() {
        return addressBook.getEmployeeList();
    }

    @Override
    public boolean hasEmployee(Employee employee) {
        requireNonNull(employee);
        return addressBook.hasEmployee(employee);
    }

    @Override
    public void deleteEmployee(Employee target) {
        addressBook.removeEmployee(target);
    }

    @Override
    public void addEmployee(Employee employee) {
        addressBook.addEmployee(employee);
        updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setEmployee(Employee target, Employee editedEmployee) {
        requireAllNonNull(target, editedEmployee);

        addressBook.setEmployee(target, editedEmployee);
    }

    //=========== EventBook ================================================================================

    @Override
    public void setEventBook(ReadOnlyEventBook eventBook) {
        this.eventBook.resetData(eventBook);
    }

    @Override
    public ReadOnlyEventBook getEventBook() {
        return eventBook;
    }

    @Override
    public ObservableList<Event> getFullListEvents() {
        return eventBook.getEventList();
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return eventBook.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) {
        eventBook.removeEvent(target);
    }

    @Override
    public void addEvent(Event event) {
        eventBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        updateFilteredScheduledEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        eventBook.setEvent(target, editedEvent);
    }

    //=========== Filtered Employee List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Employee} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        return filteredEmployees;
    }

    @Override
    public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
        requireNonNull(predicate);
        filteredEmployees.setPredicate(predicate);
    }

    //=========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedEventBook}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    //=========== Filtered Scheduled Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedEventBook}
     */
    @Override
    public ObservableList<Event> getFilteredScheduledEventList() {
        return filteredScheduledEvents;
    }

    @Override
    public void updateFilteredScheduledEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredScheduledEvents.setPredicate(predicate);
    }

    //=========== Distinct Dates List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code DistinctDates} for Employees
     */
    @Override
    public ObservableList<DistinctDate> getEmployeeDistinctDatesList() {
        return employeeDistinctDateList;
    }

    /**
     * Returns an unmodifiable view of the list of {@code DistinctDates} for Events
     */
    @Override
    public ObservableList<DistinctDate> getEventDistinctDatesList() {
        return eventDistinctDatesList;
    }

    @Override
    public void updateEmployeeDistinctDateList(List<DistinctDate> list) {
        employeeDistinctDateList = FXCollections.observableList(list);
    }

    @Override
    public void updateEventDistinctDatesList(List<DistinctDate> distinctDates) {
        eventDistinctDatesList = FXCollections.observableList(distinctDates);
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
        return eventBook.equals(other.eventBook)
                && addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredEmployees.equals(other.filteredEmployees)
                && filteredEvents.equals(other.filteredEvents);
    }

}
