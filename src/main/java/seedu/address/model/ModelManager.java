package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.id.IdManager;
import seedu.address.model.legacy.AddressBook;
import seedu.address.model.legacy.ReadOnlyAddressBook;
import seedu.address.model.pdfmanager.PdfCreator;
import seedu.address.model.pdfmanager.exceptions.PdfNoTaskToDisplayException;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;
import seedu.address.model.task.TaskManager;
import seedu.address.model.task.TaskStatus;
import seedu.address.storage.CentralManager;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    public static final String MESSAGE_NO_ASSIGNED_TASK_FOR_THE_DATE = "There's no assigned tasks for %1$s.";

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> unassignedTasks;

    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Driver> filteredDrivers;

    private final TaskManager taskManager;
    private final CustomerManager customerManager;
    private final DriverManager driverManager;
    private final IdManager idManager;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.taskManager = new TaskManager();
        this.customerManager = new CustomerManager();
        this.driverManager = new DriverManager();
        this.idManager = new IdManager();

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTasks = new FilteredList<>(this.taskManager.getList());
        unassignedTasks = new FilteredList<>(this.taskManager.getList());
        filteredCustomers = new FilteredList<>(this.customerManager.getCustomerList());
        filteredDrivers = new FilteredList<>(this.driverManager.getDriverList());
    }

    public ModelManager(CentralManager centralManager, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(centralManager, userPrefs);

        logger.fine("Initializing with central manager: " + centralManager + " and user prefs " + userPrefs);

        //temp
        //to pass addressbook test case
        this.addressBook = new AddressBook();
        filteredPersons = new FilteredList<>(addressBook.getPersonList());

        this.userPrefs = new UserPrefs(userPrefs);

        this.customerManager = centralManager.getCustomerManager();
        this.driverManager = centralManager.getDriverManager();
        this.taskManager = centralManager.getTaskManager();
        this.idManager = centralManager.getIdManager();

        filteredCustomers = new FilteredList<>(customerManager.getCustomerList());
        filteredDrivers = new FilteredList<>(driverManager.getDriverList());
        filteredTasks = new FilteredList<>(taskManager.getList());
        unassignedTasks = new FilteredList<>(taskManager.getList());
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

    // =========== CentralManager ============================================================================

    /**
     * Resets the {@link CentralManager} to a empty state. All data will be removed.
     */
    public void resetCentralManager() {
        this.taskManager.setTaskList(new TaskList());
        this.customerManager.setPersons(new CustomerManager().getPersonList());
        this.driverManager.setPersons(new DriverManager().getPersonList());
        this.idManager.resetIdManager();
    }

    // =========== Task Manager ===============================================================================

    /**
     * Adds task into task list. Records the last unique task id created in {@link IdManager}.
     */
    public void addTask(Task task) {
        taskManager.addTask(task);
        idManager.lastTaskIdPlusOne();
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
    public CustomerManager getCustomerManager() {
        return customerManager;
    }

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
        idManager.lastCustomerIdPlusOne();
    }

    public void deleteCustomer(Customer customer) {
        customerManager.removePerson(customer);
    }

    // =========== Driver Manager ===========================================================================
    public DriverManager getDriverManager() {
        return driverManager;
    }

    public boolean hasDriver(Driver driver) {
        return driverManager.hasPerson(driver);
    }

    public boolean hasDriver(int driverId) {
        return driverManager.hasDriver(driverId);
    }

    public void setDriver(Driver driverToEdit, Driver editedDriver) {
        driverManager.setPerson(driverToEdit, editedDriver);
    }
    @Override
    public void viewDriverTask(Person driverToView) {

    }

    public Driver getDriver(int driverId) {
        return driverManager.getDriver(driverId);
    }

    /**
     * Adds Driver into driver list. Records the last unique driver id created in {@link IdManager}.
     */
    public void addDriver(Driver driver) {
        driverManager.addPerson(driver);
        idManager.lastDriverIdPlusOne();
    }

    public void deleteDriver(Driver driver) {
        driverManager.removePerson(driver);
    }

    // =========== IdManager =======================================================================

    public int getNextTaskId() {
        return idManager.getNextTaskId();
    }

    public int getNextCustomerId() {
        return idManager.getNextCustomerId();
    }

    public int getNextDriverId() {
        return idManager.getNextDriverId();
    }

    public IdManager getIdManager() {
        return idManager;
    }

    public boolean isStartAfresh() {
        return idManager.isStartAfresh();
    }

    // ========= PdfCreator =========================================================================

    /**
     * Saves drivers' tasks for a specified date in PDF format.
     *
     * @param filePath directory to save the PDF file.
     * @param dateOfDelivery date of delivery.
     * @throws IOException if directory is not found.
     * @throws PdfNoTaskToDisplayException if there is no assigned task on the day.
     */
    public void saveDriverTaskPdf(String filePath, LocalDate dateOfDelivery)
            throws IOException, PdfNoTaskToDisplayException {
        requireAllNonNull(filePath, dateOfDelivery);

        List<Task> assignedTaskOnDateList = getOnlyAssignedTaskOnDate(taskManager.getList(), dateOfDelivery);
        List<Task> sortedByEventTimeTasks = getSortedByEventTimeTasks(assignedTaskOnDateList);

        if (assignedTaskOnDateList.size() == 0) {
            throw new PdfNoTaskToDisplayException(String.format(MESSAGE_NO_ASSIGNED_TASK_FOR_THE_DATE, dateOfDelivery));
        }

        List<Driver> drivers = getDriversFromTasks(assignedTaskOnDateList);
        List<Driver> sortedByNameDrivers = getSortedByNameDrivers(drivers);

        PdfCreator pdfCreator = new PdfCreator(filePath);
        pdfCreator.saveDriverTaskPdf(sortedByEventTimeTasks, sortedByNameDrivers, dateOfDelivery);
    }

    public List<Task> getOnlyAssignedTaskOnDate(List<Task> tasks, LocalDate dateOfDelivery) {
        Predicate<Task> assignedTaskOnDatePredicate = task -> task.getDate().equals(dateOfDelivery)
                && !task.getStatus().equals(TaskStatus.INCOMPLETE);
        List<Task> assignedTaskOnDateList = TaskManager.getFilteredList(tasks, assignedTaskOnDatePredicate);

        return assignedTaskOnDateList;
    }

    public List<Task> getSortedByEventTimeTasks(List<Task> tasks) {
        Comparator<Task> ascendingEventTimeComparator = Comparator.comparing(t -> {
            //uses filtered assigned tasks, so eventTime must be present
            assert t.getEventTime().isPresent();
            return t.getEventTime().get();
        });

        List<Task> sortedList = TaskManager.getSortedList(tasks, ascendingEventTimeComparator);

        return sortedList;
    }

    public List<Driver> getDriversFromTasks(List<Task> tasks) {
        return TaskManager.getDriversFromTasks(tasks);
    }

    public List<Driver> getSortedByNameDrivers(List<Driver> drivers) {
        Comparator<Driver> sortByNameComparator = Comparator.comparing(driver -> driver.getName().toString());
        List<Driver> sortedByNameDrivers = DriverManager.getSortedDriverList(drivers, sortByNameComparator);

        return sortedByNameDrivers;
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
        return customerManager.equals(other.getCustomerManager())
                && driverManager.equals(other.getDriverManager())
                && taskManager.equals(other.getTaskManager())
                && addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    // =========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the
     * internal list of {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate, FilteredList<Task> list) {
        requireNonNull(predicate);
        list.setPredicate(predicate);
    }

    /**
     * Returns an observable view of the list of that is filtered to unassigned tasks
     */
    @Override
    public ObservableList<Task> getUnassignedTaskList() {
        updateFilteredTaskList(PREDICATE_SHOW_UNASSIGNED, unassignedTasks);
        return unassignedTasks;
    }

    /**
     * Returns an observable view of the list of that is filtered to assigned tasks
     */
    @Override
    public ObservableList<Task> getAssignedTaskList() {
        updateFilteredTaskList(PREDICATE_SHOW_ASSIGNED, filteredTasks);
        return filteredTasks;
    }

    /**
     * Returns a observable view of the list of incomplete tasks from the previous days
     */
    @Override
    public ObservableList<Task> getIncompleteTaskList() {
        FilteredList<Task> incompleteTasks = new FilteredList<>(this.taskManager.getList());
        updateFilteredTaskList(PREDICATE_SHOW_ASSIGNED.and(PREDICATE_SHOW_PREVIOUS_DAYS), incompleteTasks);
        return incompleteTasks;
    }

    /**
     * Refreshes the display of task list.
     */
    @Override
    public void refreshFilteredTaskList() {
        //refresh assigned task list
        updateFilteredTaskList(PREDICATE_SHOW_EMPTY_TASKS, filteredTasks);
        getAssignedTaskList();

        //refresh unassigned task list
        updateFilteredTaskList(PREDICATE_SHOW_EMPTY_TASKS, unassignedTasks);
        getUnassignedTaskList();
    }

    /**
     * Refreshes unassigned task list, assigned task list, customer list and driver list.
     */
    public void refreshAllFilteredList() {
        refreshFilteredCustomerList();
        refreshFilteredDriverList();
        refreshFilteredTaskList();
    }

    // =========== Filtered Customer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the
     * internal list of {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return filteredCustomers;
    }

    @Override
    public void updateFilteredCustomerList(Predicate<Customer> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
    }

    /**
     * Refreshes the display of customer list.
     */
    public void refreshFilteredCustomerList() {
        updateFilteredCustomerList(PREDICATE_SHOW_EMPTY_CUSTOMERS);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    // =========== Filtered Driver List Accessors =============================================================

    @Override
    public ObservableList<Driver> getFilteredDriverList() {
        return filteredDrivers;
    }

    @Override
    public void updateFilteredDriverList(Predicate<Driver> predicate) {
        requireNonNull(predicate);
        filteredDrivers.setPredicate(predicate);
    }

    /**
     * Refreshes the display of driver list.
     */
    public void refreshFilteredDriverList() {
        updateFilteredDriverList(PREDICATE_SHOW_EMPTY_DRIVERS);
        updateFilteredDriverList(PREDICATE_SHOW_ALL_DRIVERS);
    }
}
