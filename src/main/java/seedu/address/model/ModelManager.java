package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.id.IdManager;
import seedu.address.model.legacy.AddressBook;
import seedu.address.model.legacy.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskManager;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Driver> filteredDrivers;

    private final TaskManager taskManager;
    private final CustomerManager customerManager;
    private final DriverManager driverManager;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

        this.taskManager = new TaskManager();
        this.customerManager = new CustomerManager();
        filteredCustomers = new FilteredList<>(customerManager.getCustomerList());
        this.driverManager = new DriverManager();
        filteredDrivers = new FilteredList<>(driverManager.getDriverList());

        // temp
        // to test the task commands
        Customer testCustomer = new Customer(1, new Name("Alesx Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"), new HashSet<Tag>());
        customerManager.addPerson(testCustomer);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    // =========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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

    // =========== AddressBook ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
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

    // =========== Task Manager ===============================================================================

    /**
     * Adds task into task list. Records the last unique task id created in {@link IdManager}.
     */
    public void addTask(Task task) {
        taskManager.addTask(task);
        IdManager.lastTaskIdPlusOne();
    }

    public void deleteTask(Task task) {
        taskManager.deleteTask(task);
    }

    public boolean hasTask(Task task) {
        return taskManager.hasTask(task);
    }

    public boolean hasTask(int taskId) {
        return taskManager.hasTask(taskId);
    }

    public void setTask(Task taskToEdit, Task editedTask) {
        taskManager.setTask(taskToEdit, editedTask);
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public Task getTask(int taskId) {
        return taskManager.getTask(taskId);
    }

    /**
     * Checks if driver is allocated to any task.
     */
    public boolean hasTaskBelongsToDriver(Driver driver) {
        return taskManager.getList()
                .stream()
                .anyMatch(task -> {
                    if (task.getDriver().isEmpty()) {
                        return false;
                    }

                    return task.getDriver().get().equals(driver);
                });
    }

    /**
     * Checks if customer is allocated to any task.
     */
    public boolean hasTaskBelongsToCustomer(Customer customer) {
        return taskManager.getList()
                .stream()
                .anyMatch(task -> task.getCustomer().equals(customer));
    }

    // =========== Customer Manager ===========================================================================
    public boolean hasCustomer(Customer customer) {
        return customerManager.hasPerson(customer);
    }

    public boolean hasCustomer(int customerId) {
        return customerManager.hasCustomer(customerId);
    }

    public void setCustomer(Customer customerToEdit, Customer editedCustomer) {
        customerManager.setCustomer(customerToEdit, editedCustomer);
    }

    public Customer getCustomer(int customerId) {
        return customerManager.getCustomer(customerId);
    }


    /**
     * Adds Customer into customer list. Records the last unique customer id created in {@link IdManager}.
     */
    public void addCustomer(Customer customer) {
        customerManager.addPerson(customer);
        IdManager.lastCustomerIdPlusOne();
    }

    public void deleteCustomer(Customer customer) {
        customerManager.removePerson(customer);
    }

    // =========== Driver Manager ===========================================================================
    public boolean hasDriver(Driver driver) {
        return driverManager.hasDriver(driver);
    }

    public boolean hasDriver(int driverId) {
        return driverManager.hasDriver(driverId);
    }

    public void setDriver(Driver driverToEdit, Driver editedDriver) {
        driverManager.setDriver(driverToEdit, editedDriver);
    }

    public Driver getDriver(int driverId) {
        return driverManager.getDriver(driverId);
    }

    /**
     * Adds Driver into driver list. Records the last unique driver id created in {@link IdManager}.
     */
    public void addDriver(Driver driver) {
        driverManager.addDriver(driver);
        IdManager.lastDriverIdPlusOne();
    }

    public void deleteDriver(Driver driver) {
        driverManager.deleteDriver(driver);
    }

    // =========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the
     * internal list of {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return filteredCustomers;
    }

    @Override
    public ObservableList<Driver> getFilteredDriverList() {
        return filteredDrivers;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredCustomerList(Predicate<Customer> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
    }

    @Override
    public void updateFilteredDriverList(Predicate<Driver> predicate) {
        requireNonNull(predicate);
        filteredDrivers.setPredicate(predicate);
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
        return addressBook.equals(other.addressBook) && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
