package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.legacy.ReadOnlyAddressBook;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskManager;

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

    // customer manager

    boolean hasCustomer(Customer customer);

    boolean hasCustomer(int customerId);

    Customer getCustomer(int customerId);

    void setCustomer(Customer customerToEdit, Customer editedTask);

    void updateFilteredCustomerList(Predicate<Customer> predicate);

    ObservableList<Customer> getFilteredCustomerList();

    void addCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    // driver manager

    boolean hasDriver(Driver driver);

    boolean hasDriver(int driverId);

    Driver getDriver(int driverId);

    void setDriver(Driver driverToEdit, Driver editedTask);

    void updateFilteredDriverList(Predicate<Driver> predicate);

    ObservableList<Driver> getFilteredDriverList();

    void addDriver(Driver driver);

    void deleteDriver(Driver driver);
}
