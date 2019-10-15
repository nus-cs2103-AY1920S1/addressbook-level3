package seedu.deliverymans.model;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.deliverymans.commons.core.GuiSettings;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.logic.parser.universal.Context;
import seedu.deliverymans.model.addressbook.AddressBook;
import seedu.deliverymans.model.addressbook.ReadOnlyAddressBook;
import seedu.deliverymans.model.addressbook.person.Person;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.database.*;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final OrderBook orderBook;
    private final CustomerDatabase customerDatabase;
    private final RestaurantDatabase restaurantDatabase;

    private final UserPrefs userPrefs;

    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Order> filteredOrders;
    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Restaurant> filteredRestaurants;

    private Context context;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook,
                        ReadOnlyCustomerDatabase customerDatabase,
                        ReadOnlyRestaurantDatabase restaurantDatabase,
                        ReadOnlyOrderBook orderBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, customerDatabase, restaurantDatabase, orderBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.customerDatabase = new CustomerDatabase(customerDatabase);
        this.restaurantDatabase = new RestaurantDatabase(restaurantDatabase);
        this.orderBook = new OrderBook(orderBook);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredCustomers = new FilteredList<>(this.customerDatabase.getCustomerList());
        filteredRestaurants = new FilteredList<>(this.restaurantDatabase.getRestaurantList());
        filteredOrders = new FilteredList<>(this.orderBook.getOrderList());

        context = Context.GLOBAL;
    }

    public ModelManager() {
        this(new AddressBook(), new CustomerDatabase(), new RestaurantDatabase(), new OrderBook(), new UserPrefs());
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

    //=========== AddressBook ================================================================================

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

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

    //=========== Universal Methods =============================================================

    /**
     * Sets current context of the system.
     *
     * @param context current context
     */
    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    //=========== Customer Methods =============================================================

    @Override
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return customerDatabase.hasCustomer(customer);
    }

    @Override
    public void deleteCustomer(Customer target) {
        customerDatabase.removeCustomer(target);
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDatabase.addCustomer(customer);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);

        customerDatabase.setCustomer(target, editedCustomer);
    }

    //=========== Restaurant Methods =============================================================
    @Override
    public Path getRestaurantDatabaseFilePath() {
        return userPrefs.getRestaurantDatabaseFilePath();
    }

    @Override
    public void setRestaurantDatabaseFilePath(Path restaurantDatabaseFilePath) {
        requireNonNull(restaurantDatabaseFilePath);
        userPrefs.setRestaurantDatabaseFilePath(restaurantDatabaseFilePath);
    }

    @Override
    public void setRestaurantDatabase(ReadOnlyRestaurantDatabase restaurantDatabase) {
        this.restaurantDatabase.resetData(restaurantDatabase);
    }

    @Override
    public ReadOnlyRestaurantDatabase getRestaurantDatabase() {
        return restaurantDatabase;
    }

    @Override
    public boolean hasRestaurant(Restaurant restaurant) {
        requireNonNull(restaurant);
        return restaurantDatabase.hasRestaurant(restaurant);
    }

    @Override
    public void deleteRestaurant(Restaurant target) {
        restaurantDatabase.removeRestaurant(target);
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        restaurantDatabase.addRestaurant(restaurant);
        updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
    }

    @Override
    public void setRestaurant(Restaurant target, Restaurant editedRestaurant) {
        requireAllNonNull(target, editedRestaurant);

        restaurantDatabase.setRestaurant(target, editedRestaurant);
    }
    //=========== Deliveryman Methods =============================================================

    @Override
    public boolean hasDeliveryman(Deliveryman deliveryman) {
        requireNonNull(deliveryman);
        return true;
    }

    @Override
    public void addDeliveryman(Deliveryman deliveryman) {
        requireNonNull(deliveryman);
    }

    //=========== Order Methods =============================================================
    @Override
    public Path getOrderBookFilePath() {
        return userPrefs.getOrderBookFilePath();
    }

    @Override
    public void setOrderBookFilePath(Path orderBookFilePath) {
        requireNonNull(orderBookFilePath);
        userPrefs.setOrderBookFilePath(orderBookFilePath);
    }

    @Override
    public void setOrderBook(ReadOnlyOrderBook orderBook) {
        this.orderBook.resetData(orderBook);
    }

    @Override
    public ReadOnlyOrderBook getOrderBook() {
        return orderBook;
    }

    @Override
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orderBook.hasOrder(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderBook.removeOrder(order);
    }

    @Override
    public void addOrder(Order order) {
        orderBook.addOrder(order);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        orderBook.setOrder(target, editedOrder);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Customer} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return filteredCustomers;
    }

    @Override
    public ObservableList<Restaurant> getFilteredRestaurantList() {
        return filteredRestaurants;
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }

    @Override
    public void updateFilteredCustomerList(Predicate<Customer> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
    }

    @Override
    public void updateFilteredRestaurantList(Predicate<Restaurant> predicate) {
        requireNonNull(predicate);
        filteredRestaurants.setPredicate(predicate);
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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }
}
