package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;


/**
 * Represents the in-memory model of the SML data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;

    private final CustomerBook customerBook;
    private final PhoneBook phoneBook;
    private final OrderBook orderBook;
    private final ScheduleBook scheduleBook;

    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Phone> filteredPhones;
    private final FilteredList<Order> filteredOrders;
    private final FilteredList<Schedule> filteredSchedules;

    private final UserPrefs userPrefs;


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

        this.customerBook = new CustomerBook();
        this.phoneBook = new PhoneBook();
        this.orderBook = new OrderBook();
        this.scheduleBook = new ScheduleBook();

        this.filteredCustomers = new FilteredList<>(this.customerBook.getList());
        this.filteredPhones = new FilteredList<>(this.phoneBook.getList());
        this.filteredOrders = new FilteredList<>(this.orderBook.getList());
        this.filteredSchedules = new FilteredList<>(this.scheduleBook.getList());

    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyDataBook<Customer> customerBook, ReadOnlyDataBook<Phone> phoneBook,
                        ReadOnlyDataBook<Order> orderBook, ReadOnlyDataBook<Schedule> scheduleBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(customerBook, userPrefs);

        logger.fine("Initializing with customer book: " + customerBook + " and user prefs " + userPrefs);

        this.customerBook = new CustomerBook(customerBook);
        this.phoneBook = new PhoneBook(phoneBook);
        this.orderBook = new OrderBook(orderBook);
        this.scheduleBook = new ScheduleBook(scheduleBook);

        this.userPrefs = new UserPrefs(userPrefs);

        this.filteredCustomers = new FilteredList<>(this.customerBook.getList());
        this.filteredPhones = new FilteredList<>(this.phoneBook.getList());
        this.filteredOrders = new FilteredList<>(this.orderBook.getList());
        this.filteredSchedules = new FilteredList<>(this.scheduleBook.getList());

        this.addressBook = new AddressBook();
        this.filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

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
     * Returns an unmodifiable view of the list of {@code Customer} backed by the internal list of
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

    //=========== customerBook ================================================================================

    @Override
    public void setCustomerBook(ReadOnlyDataBook<Customer> customerBook) {
        this.customerBook.resetData(customerBook);
    }

    @Override
    public ReadOnlyDataBook<Customer> getCustomerBook() {

        return customerBook;
    }

    @Override
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return customerBook.hasCustomer(customer);
    }

    @Override
    public void deleteCustomer(Customer target) {
        customerBook.removeCustomer(target);
    }

    @Override
    public void addCustomer(Customer customer) {
        customerBook.addCustomer(customer);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);

        customerBook.setCustomer(target, editedCustomer);
    }

    //=========== Filtered Customer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Customer} backed by the internal list of
     * {@code versionedAddressBook}
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

    //=========== phoneBook ================================================================================

    @Override
    public void setPhoneBook(ReadOnlyDataBook<Phone> phoneBook) {
        this.phoneBook.resetData(phoneBook);
    }

    @Override
    public ReadOnlyDataBook<Phone> getPhoneBook() {
        return phoneBook;
    }

    @Override
    public boolean hasPhone(Phone phone) {
        requireNonNull(phone);
        return phoneBook.hasPhone(phone);
    }

    @Override
    public void deletePhone(Phone target) {
        phoneBook.removePhone(target);
    }

    @Override
    public void addPhone(Phone phone) {
        phoneBook.addPhone(phone);
        updateFilteredPhoneList(PREDICATE_SHOW_ALL_PHONES);
    }

    @Override
    public void setPhone(Phone target, Phone editedPhone) {
        requireAllNonNull(target, editedPhone);

        phoneBook.setPhone(target, editedPhone);
    }

    //=========== Filtered Phone List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Phone} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Phone> getFilteredPhoneList() {
        return filteredPhones;
    }

    @Override
    public void updateFilteredPhoneList(Predicate<Phone> predicate) {
        requireNonNull(predicate);
        filteredPhones.setPredicate(predicate);
    }

    //=========== OrderBook ================================================================================

    @Override
    public void setOrderBook(ReadOnlyDataBook<Order> orderBook) {
        this.orderBook.resetData(orderBook);
    }

    @Override
    public ReadOnlyDataBook<Order> getOrderBook() {
        return orderBook;
    }

    @Override
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orderBook.hasOrder(order);
    }

    @Override
    public void deleteOrder(Order target) {
        orderBook.removeOrder(target);
    }

    @Override
    public void addOrder(Order order) {
        orderBook.addOrder(order);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDER);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        orderBook.setOrder(target, editedOrder);
    }

    //=========== Filtered Order List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Order} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }

    //=========== ScheduleBook ================================================================================

    @Override
    public void setScheduleBook(ReadOnlyDataBook<Schedule> scheduleBook) {
        this.scheduleBook.resetData(scheduleBook);
    }

    @Override
    public ReadOnlyDataBook<Schedule> getScheduleBook() {
        return scheduleBook;
    }

    @Override
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return scheduleBook.hasSchedule(schedule);
    }

    @Override
    public void deleteSchedule(Schedule target) {
        scheduleBook.removeSchedule(target);
    }

    @Override
    public void addSchedule(Schedule schedule) {
        scheduleBook.addSchedule(schedule);
        updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULE);
    }

    @Override
    public void setSchedule(Schedule target, Schedule editedSchedule) {
        requireAllNonNull(target, editedSchedule);

        scheduleBook.setSchedule(target, editedSchedule);
    }

    //=========== Filtered Schedule List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Schedule} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Schedule> getFilteredScheduleList() {
        return filteredSchedules;
    }

    @Override
    public void updateFilteredScheduleList(Predicate<Schedule> predicate) {
        requireNonNull(predicate);
        filteredSchedules.setPredicate(predicate);
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
        return customerBook.equals(other.customerBook)
                && phoneBook.equals(other.phoneBook)
                && orderBook.equals(other.orderBook)
                && scheduleBook.equals(other.scheduleBook)
                && userPrefs.equals(other.userPrefs)
                && filteredCustomers.equals(other.filteredCustomers)
                && filteredPhones.equals(other.filteredPhones)
                && filteredOrders.equals(other.filteredOrders)
                && filteredSchedules.equals(other.filteredSchedules);
    }

}
