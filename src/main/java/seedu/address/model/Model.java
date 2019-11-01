package seedu.address.model;

import java.nio.file.Path;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Customer> PREDICATE_SHOW_ALL_CUSTOMERS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Phone> PREDICATE_SHOW_ALL_PHONES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Order> PREDICATE_SHOW_ALL_ORDER = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Schedule> PREDICATE_SHOW_ALL_SCHEDULE = unused -> true;

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

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    ////customer operations

    /**
     * Replaces address book data with the data in {@code customerBook}.
     */
    void setCustomerBook(ReadOnlyDataBook<Customer> customerBook);

    /** Returns the customer DataBook */
    ReadOnlyDataBook<Customer> getCustomerBook();

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    boolean hasCustomer(Customer customer);

    /**
     * Deletes the given customer.
     * The customer must exist in the address book.
     */
    void deleteCustomer(Customer target);

    /**
     * Adds the given customer.
     * {@code customer} must not already exist in the address book.
     */
    void addCustomer(Customer customer);

    /**
     * Replaces the given customer {@code target} with {@code editedCustomer}.
     * {@code target} must exist in the customer book.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer.
     */
    void setCustomer(Customer target, Customer editedCustomer);

    /** Returns an unmodifiable view of the filtered customer list */
    ObservableList<Customer> getFilteredCustomerList();

    /**
     * Updates the filter of the filtered customer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCustomerList(Predicate<Customer> predicate);

    //// phone operations

    /**
     * Replaces phone book data with the data in {@code phoneBook}.
     */
    void setPhoneBook(ReadOnlyDataBook<Phone> phoneBook);

    /** Returns the Phone DataBook */
    ReadOnlyDataBook<Phone> getPhoneBook();

    /**
     * Returns true if a phone with the same identity as {@code phone} exists in the phone book.
     */
    boolean hasPhone(Phone phone);

    /**
     * Deletes the given phone.
     * The phone must exist in the phone book.
     */
    void deletePhone(Phone target);

    /**
     * Adds the given phone.
     * {@code phone} must not already exist in the address book.
     */
    void addPhone(Phone phone);

    /**
     * Replaces the given phone {@code target} with {@code editedPhone}.
     * {@code target} must exist in the address book.
     * The phone identity of {@code editedPhone} must not be the same as another existing phone in the address book.
     */
    void setPhone(Phone target, Phone editedPhone);

    /** Returns an unmodifiable view of the filtered phone list */
    ObservableList<Phone> getFilteredPhoneList();

    /**
     * Updates the filter of the filtered phone list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPhoneList(Predicate<Phone> predicate);

    ////order operations

    /**
     * Replaces order book data with the data in {@code orderBook}.
     */
    void setOrderBook(ReadOnlyDataBook<Order> orderBook);

    /** Returns the AddressBook */
    ReadOnlyDataBook<Order> getOrderBook();

    /**
     * Returns true if a order with the same identity as {@code order} exists in the address book.
     */
    boolean hasOrder(Order order);

    /**
     * Deletes the given order.
     * The order must exist in the address book.
     */
    void deleteOrder(Order target);

    /**
     * Adds the given order.
     * {@code order} must not already exist in the address book.
     */
    void addOrder(Order order);

    /**
     * Replaces the given order {@code target} with {@code editedOrder}.
     * {@code target} must exist in the address book.
     * The order identity of {@code editedOrder} must not be the same as another existing order in the address book.
     */
    void setOrder(Order target, Order editedOrder);

    /** Returns an unmodifiable view of the filtered order list */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Order> predicate);

    ////schedule operations

    /**
     * Replaces schedule book data with the data in {@code scheduleBook}.
     */
    void setScheduleBook(ReadOnlyDataBook<Schedule> scheduleBook);

    /** Returns the AddressBook */
    ReadOnlyDataBook<Schedule> getScheduleBook();

    /**
     * Returns true if a schedule with the same identity as {@code schedule} exists in the address book.
     */
    boolean hasSchedule(Schedule schedule);

    /**
     * Deletes the given schedule.
     * The schedule must exist in the address book.
     */
    void deleteSchedule(Schedule target);

    /**
     * Adds the given schedule.
     * {@code schedule} must not already exist in the address book.
     */
    void addSchedule(Schedule schedule);

    /**
     * Replaces the given schedule {@code target} with {@code editedSchedule}.
     * {@code target} must exist in the address book.
     * The schedule identity of {@code editedSchedule} must not be the same as another existing schedule.
     */
    void setSchedule(Schedule target, Schedule editedSchedule);

    /** Returns an unmodifiable view of the filtered schedule list */
    ObservableList<Schedule> getFilteredScheduleList();

    /**
     * Updates the filter of the filtered schedule list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredScheduleList(Predicate<Schedule> predicate);

    /**
     * Gets the list conflicting schedules if any.
     */
    List<Schedule> getConflictingSchedules(Schedule schedule);

    /**
     * Returns the CalendarDate object.
     */
    CalendarDate getCalendarDate();

    /**
     * Sets the calendar object in the property
     */
    void setCalendarDate(Calendar calendar);

    ////order operations

    /**
     * Replaces archived order book data with the data in {@code orderBook}.
     */
    void setArchivedOrderBook(ReadOnlyDataBook<Order> archivedOrderBook);

    /** Returns the AddressBook */
    ReadOnlyDataBook<Order> getArchivedOrderBook();

    /**
     * Returns true if a order with the same identity as {@code order} exists in the archived order book.
     */
    boolean hasArchivedOrder(Order order);

    /**
     * Deletes the given order.
     * The order must exist in the address book.
     */
    void deleteArchivedOrder(Order target);

    /**
     * Adds the given archived order.
     * {@code order} must not already exist in the archived order book.
     */
    void addArchivedOrder(Order order);

    /**
     * Replaces the given order {@code target} with {@code editedOrder}.
     * {@code target} must exist in the archived order book.
     * The order identity of {@code editedOrder} must not be the same as another
     * existing order in the archived order book.
     */
    void setArchivedOrder(Order target, Order editedOrder);

    /** Returns an unmodifiable view of the filtered archived order list */
    ObservableList<Order> getFilteredArchivedOrderList();

    /**
     * Updates the filter of the filtered archived order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredArchivedOrderList(Predicate<Order> predicate);

    /**
     * Places all completed and cancelled orders in archivedOrderBook or
     * orderBook if otherwise.
     */
    void resolveOrderBooksConflict();
}
