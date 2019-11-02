package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.order.Status;
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

    private final DataBook<Customer> customerBook;
    private final DataBook<Phone> phoneBook;
    private final DataBook<Order> orderBook;
    private final DataBook<Schedule> scheduleBook;
    private final DataBook<Order> archivedOrderBook;

    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Phone> filteredPhones;
    private final FilteredList<Order> filteredOrders;
    private final FilteredList<Schedule> filteredSchedules;
    private final FilteredList<Order> filteredArchivedOrders;

    private final UserPrefs userPrefs;
    private final CalendarDate calendarDate;

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

        this.customerBook = new DataBook<>();
        this.phoneBook = new DataBook<>();
        this.orderBook = new DataBook<>();
        this.scheduleBook = new DataBook<>();
        this.archivedOrderBook = new DataBook<>();

        this.filteredCustomers = new FilteredList<>(this.customerBook.getList());
        this.filteredPhones = new FilteredList<>(this.phoneBook.getList());
        this.filteredOrders = new FilteredList<>(this.orderBook.getList());
        this.filteredSchedules = new FilteredList<>(this.scheduleBook.getList());
        this.filteredArchivedOrders = new FilteredList<>(this.archivedOrderBook.getList());

        this.calendarDate = new CalendarDate(Calendar.getInstance());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyDataBook<Customer> customerBook, ReadOnlyDataBook<Phone> phoneBook,
                        ReadOnlyDataBook<Order> orderBook, ReadOnlyDataBook<Schedule> scheduleBook,
                        ReadOnlyDataBook<Order> archivedOrderBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(customerBook, phoneBook, orderBook, scheduleBook, userPrefs);

        logger.fine("Initializing with customer book: " + customerBook + " and user prefs " + userPrefs);

        this.customerBook = new DataBook<>(customerBook);
        this.phoneBook = new DataBook<>(phoneBook);
        this.orderBook = new DataBook<>(orderBook);
        this.scheduleBook = new DataBook<>(scheduleBook);
        this.archivedOrderBook = new DataBook<>(archivedOrderBook);

        resolveOrderBooksConflict();

        this.userPrefs = new UserPrefs(userPrefs);
        this.calendarDate = new CalendarDate(Calendar.getInstance());

        this.filteredCustomers = new FilteredList<>(this.customerBook.getList());
        this.filteredPhones = new FilteredList<>(this.phoneBook.getList());
        this.filteredOrders = new FilteredList<>(this.orderBook.getList());
        this.filteredSchedules = new FilteredList<>(this.scheduleBook.getList());
        this.filteredArchivedOrders = new FilteredList<>(this.archivedOrderBook.getList());

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
        return customerBook.has(customer);
    }

    @Override
    public void deleteCustomer(Customer target) {
        customerBook.remove(target);

        // cascade
        List<Order> orders = orderBook.getList();
        for (int i = orders.size() - 1; i >= 0; i--) {
            Order order = orders.get(i);
            if (order.getCustomer().equals(target)) {
                deleteOrder(order);
                break;
            }
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        customerBook.add(customer);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);
        customerBook.set(target, editedCustomer);

        // cascade
        List<Order> orders = orderBook.getList();
        for (int i = orders.size() - 1; i >= 0; i--) {
            Order order = orders.get(i);
            if (order.getCustomer().equals(target)) {
                Order editedOrder = new Order(order.getId(), editedCustomer, order.getPhone(),
                        order.getPrice(), order.getStatus(), order.getSchedule(), order.getTags());
                orderBook.set(order, editedOrder);
                break;
            }
        }
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
        return phoneBook.has(phone);
    }

    @Override
    public void deletePhone(Phone target) {
        phoneBook.remove(target);

        // cascade

        List<Order> orders = orderBook.getList();

        for (int i = orders.size() - 1; i >= 0; i--) {
            Order order = orders.get(i);
            if (order.getPhone().equals(target)) {
                deleteOrder(order);
                break;
            }
        }
    }

    @Override
    public void addPhone(Phone phone) {
        phoneBook.add(phone);
        updateFilteredPhoneList(PREDICATE_SHOW_ALL_PHONES);
    }

    @Override
    public void setPhone(Phone target, Phone editedPhone) {
        requireAllNonNull(target, editedPhone);
        phoneBook.set(target, editedPhone);

        // cascade
        List<Order> orders = orderBook.getList();

        for (int i = orders.size() - 1; i >= 0; i--) {
            Order order = orders.get(i);
            if (order.getPhone().equals(target)) {
                Order editedOrder = new Order(order.getId(), order.getCustomer(), editedPhone,
                        order.getPrice(), order.getStatus(), order.getSchedule(), order.getTags());
                setOrder(order, editedOrder);
                break;
            }
        }
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

    //=========== Order DataBook ================================================================================

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
        return orderBook.has(order);
    }

    @Override
    public void deleteOrder(Order target) {
        orderBook.remove(target);

        // cascade
        Optional<Schedule> targetSchedule = target.getSchedule();
        if (targetSchedule.isPresent() && hasSchedule(targetSchedule.get())) {
            deleteSchedule(targetSchedule.get());
        }
    }

    @Override
    public void addOrder(Order order) {
        orderBook.add(order);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDER);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        orderBook.set(target, editedOrder);
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

    //=========== Schedule DataBook ================================================================================

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
        return scheduleBook.has(schedule);
    }

    @Override
    public void deleteSchedule(Schedule target) {
        scheduleBook.remove(target);
        setCalendarDate(target.getCalendar());


        // cascade
        List<Order> orders = orderBook.getList();
        for (Order order : orders) {
            order.getSchedule().ifPresent(schedule -> {
                if (schedule.equals(target)) {
                    Order editedOrder = new Order(order.getId(), order.getCustomer(), order.getPhone(),
                            order.getPrice(), Status.UNSCHEDULED, Optional.empty(), order.getTags());
                    setOrder(order, editedOrder);
                }
            });
        }
    }

    @Override
    public void addSchedule(Schedule schedule) {
        scheduleBook.add(schedule);
        setCalendarDate(schedule.getCalendar());
        updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULE);
    }

    @Override
    public void setSchedule(Schedule target, Schedule editedSchedule) {
        requireAllNonNull(target, editedSchedule);

        scheduleBook.set(target, editedSchedule);
        setCalendarDate(editedSchedule.getCalendar());

        // cascade
        List<Order> orders = orderBook.getList();
        for (Order order : orders) {
            order.getSchedule().ifPresent(schedule -> {
                if (schedule.equals(target)) {
                    Order editedOrder = new Order(order.getId(), order.getCustomer(), order.getPhone(),
                            order.getPrice(), order.getStatus(), Optional.of(editedSchedule), order.getTags());
                    orderBook.set(order, editedOrder);
                }
            });
        }
    }

    @Override
    public List<Schedule> getConflictingSchedules(Schedule schedule) {
        requireNonNull(schedule);
        List<Schedule> conflicts = new ArrayList<>();

        Calendar startTime = schedule.getCalendar();
        Calendar earliestUnconflictedStartTime = (Calendar) startTime.clone();
        earliestUnconflictedStartTime.add(Calendar.HOUR_OF_DAY, -1);
        Calendar latestUnconflictedStartTime = (Calendar) startTime.clone();
        latestUnconflictedStartTime.add(Calendar.HOUR_OF_DAY, 1);

        List<Schedule> schedules = scheduleBook.getList();

        // defensive filter for orderless schedule - in 0 orders
        // extra filter for same schedule
        schedules.stream()
                .filter(x -> 0 != orderBook.getList().stream()
                        .filter(y -> y.getSchedule().isPresent())
                        .filter(y -> y.getSchedule().get().isSameAs(x))
                        .count())
                .filter(x -> !x.isSameAs(schedule))
                .filter(x -> x.getCalendar().after(earliestUnconflictedStartTime))
                .filter(x -> x.getCalendar().before(latestUnconflictedStartTime))
                .sorted(Comparator.comparing(Schedule::getCalendar))
                .forEach(conflicts::add);

        // dummy use - Collections to import (so it is not unused)
        Collections.sort(conflicts, Comparator.comparing(Schedule::getCalendar));

        return conflicts;
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

    //=========== CalendarDate ================================================================================

    @Override
    public void setCalendarDate(Calendar calendar) {
        requireNonNull(calendar);
        calendarDate.setCalendar(calendar);
    }

    @Override
    public CalendarDate getCalendarDate() {
        return calendarDate;
    }

    //=========== Archived Order DataBook ======================================================================

    @Override
    public void setArchivedOrderBook(ReadOnlyDataBook<Order> archivedOrderBook) {
        this.archivedOrderBook.resetData(archivedOrderBook);
    }

    @Override
    public ReadOnlyDataBook<Order> getArchivedOrderBook() {
        return archivedOrderBook;
    }

    @Override
    public boolean hasArchivedOrder(Order archivedOrder) {
        requireNonNull(archivedOrder);
        return archivedOrderBook.has(archivedOrder);
    }

    @Override
    public void deleteArchivedOrder(Order target) {
        archivedOrderBook.remove(target);

    }

    @Override
    public void addArchivedOrder(Order archivedOrder) {
        archivedOrderBook.add(archivedOrder);
        updateFilteredArchivedOrderList(PREDICATE_SHOW_ALL_ORDER);
    }

    @Override
    public void setArchivedOrder(Order target, Order editedArchived) {
        requireAllNonNull(target, editedArchived);

        archivedOrderBook.set(target, editedArchived);
    }

    //=========== Filtered Order List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ArchivedOrder} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Order> getFilteredArchivedOrderList() {
        return filteredArchivedOrders;
    }

    @Override
    public void updateFilteredArchivedOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredArchivedOrders.setPredicate(predicate);
    }

    @Override
    public void resolveOrderBooksConflict() {
        List<Order> orders = orderBook.getList();

        for (int i = orders.size() - 1; i >= 0; i--) {
            Order o = orders.get(i);
            if (o.getStatus().equals(Status.CANCELLED) || o.getStatus().equals(Status.COMPLETED)) {
                orderBook.remove(o);

                if (!archivedOrderBook.has(o)) {
                    archivedOrderBook.add(o);
                }
            }
        }

        List<Order> archivedOrders = archivedOrderBook.getList();

        for (int i = archivedOrders.size() - 1; i >= 0; i--) {
            Order o = archivedOrders.get(i);
            if (!o.getStatus().equals(Status.CANCELLED) && !o.getStatus().equals(Status.COMPLETED)) {
                archivedOrderBook.remove(o);

                if (!orderBook.has(o)) {
                    orderBook.add(o);
                }
            }
        }
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
                && archivedOrderBook.equals(other.archivedOrderBook)
                && userPrefs.equals(other.userPrefs)
                && filteredCustomers.equals(other.filteredCustomers)
                && filteredPhones.equals(other.filteredPhones)
                && filteredOrders.equals(other.filteredOrders)
                && filteredSchedules.equals(other.filteredSchedules)
                && filteredArchivedOrders.equals(other.filteredArchivedOrders);
    }

}
