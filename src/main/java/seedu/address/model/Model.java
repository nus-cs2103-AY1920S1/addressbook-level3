package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.id.IdManager;
import seedu.address.model.legacy.ReadOnlyAddressBook;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskManager;
import seedu.address.model.task.TaskStatus;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Customer> PREDICATE_SHOW_ALL_CUSTOMERS = unused -> true;
    Predicate<Driver> PREDICATE_SHOW_ALL_DRIVERS = unused -> true;

    /**
     * {@code Predicate} that filters the task to incomplete status
     */
    Predicate<Task> PREDICATE_SHOW_UNASSIGNED = task -> task.getStatus().equals(TaskStatus.INCOMPLETE);

    /**
     * {@code Predicate} that filters the task to on-going status
     */
    Predicate<Task> PREDICATE_SHOW_ASSIGNED = task -> task.getStatus().equals(TaskStatus.ON_GOING);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

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
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person. The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person. {@code person} must not already exist in the address
     * book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book. The person identity of
     * {@code editedPerson} must not be the same as another existing person in the
     * address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list.
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given
     * {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    // task manager

    void addTask(Task task);

    void deleteTask(Task task);

    boolean hasTask(Task task);

    boolean hasTask(int taskId);

    Task getTask(int taskId);

    void setTask(Task taskToEdit, Task editedTask);

    TaskManager getTaskManager();

    boolean hasTaskBelongsToDriver(Driver driver);

    boolean hasTaskBelongsToCustomer(Customer customer);

    /**
     * Returns an unmodifiable view of the filtered unassigned task list.
     */
    ObservableList<Task> getUnassignedTaskList();

    /**
     * Returns an unmodifiable view of the filtered assigned task list.
     */
    ObservableList<Task> getAssignedTaskList();

    // customer manager

    CustomerManager getCustomerManager();

    boolean hasCustomer(Customer customer);

    boolean hasCustomer(int customerId);

    Customer getCustomer(int customerId);

    void viewDriverTask(Person driverToView);

    void setCustomer(Customer customerToEdit, Customer editedTask);

    void addCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    // driver manager

    DriverManager getDriverManager();

    boolean hasDriver(Driver driver);

    boolean hasDriver(int driverId);

    Driver getDriver(int driverId);

    void setDriver(Driver driverToEdit, Driver editedTask);

    void updateFilteredDriverList(Predicate<Driver> predicate);

    void addDriver(Driver driver);

    void deleteDriver(Driver driver);

    /**
     * Returns an unmodifiable view of the filtered task list.
     */

    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given
     * {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate, FilteredList<Task> list);

    /**
     * Returns an unmodifiable view of the filtered customer list.
     */
    ObservableList<Customer> getFilteredCustomerList();

    /**
     * Updates the filter of the filtered customer list to filter by the given
     * {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCustomerList(Predicate<Customer> predicate);

    /**
     * Returns an unmodifiable view of the filtered driver list.
     */
    ObservableList<Driver> getFilteredDriverList();

    int getNextTaskId();

    int getNextCustomerId();

    int getNextDriverId();

    IdManager getIdManager();
}
