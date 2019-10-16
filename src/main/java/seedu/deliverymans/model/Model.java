package seedu.deliverymans.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.deliverymans.commons.core.GuiSettings;
import seedu.deliverymans.logic.parser.universal.Context;
import seedu.deliverymans.model.addressbook.ReadOnlyAddressBook;
import seedu.deliverymans.model.addressbook.person.Person;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.database.ReadOnlyOrderBook;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Customer> PREDICATE_SHOW_ALL_CUSTOMERS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Deliveryman> PREDICATE_SHOW_ALL_DELIVERYMEN = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Restaurant> PREDICATE_SHOW_ALL_RESTAURANTS = unused -> true;

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

    //=========== AddressBook Database / Filepath methods =====================================================

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

    //=========== Restaurant Database / Filepath methods =============================================================

    /**
     * Returns the user prefs' restaurant database file path.
     */
    Path getRestaurantDatabaseFilePath();

    /**
     * Sets the user prefs' restaurant database file path.
     */
    void setRestaurantDatabaseFilePath(Path restaurantDatabaseFilePath);

    /**
     * Replaces restaurant database data with the data in {@code restaurantDatabase}.
     */
    void setRestaurantDatabase(ReadOnlyRestaurantDatabase restaurantDatabase);

    /** Returns the RestaurantDatabase */
    ReadOnlyRestaurantDatabase getRestaurantDatabase();

    //=========== Order Database / Filepath methods =============================================================

    /**
     * Returns the user prefs' order book file path.
     */
    Path getOrderBookFilePath();

    /**
     * Sets the user prefs' order book file path.
     */
    void setOrderBookFilePath(Path orderBookFilePath);

    /**
     * Replaces order book data with the data in {@code OrderBook}.
     */
    void setOrderBook(ReadOnlyOrderBook orderBook);

    /** Returns the OrderBook */
    ReadOnlyOrderBook getOrderBook();

    //=========== Person methods =============================================================

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

    /** Tells the model that a command was run and the text of the command */
    void notifyChange(String commandText);

    /** Returns whether there are actions to undo */
    boolean hasUndo();

    /** Returns whether there are actions to redo */
    boolean hasRedo();

    /** Undoes an action */
    String undo();

    /** Redoes an action */
    String redo();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //=========== Universal methods =============================================================

    /**
     * Sets current context of the system.
     *
     * @param context current context
     */
    void setContext(Context context);

    Context getContext();

    //=========== Customer methods =============================================================

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
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer in the address
     * book.
     */
    void setCustomer(Customer target, Customer editedCustomer);

    /** Returns an unmodifiable view of the filtered customer list */
    ObservableList<Customer> getFilteredCustomerList();

    /**
     * Updates the filter of the filtered customer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCustomerList(Predicate<Customer> predicate);

    //=========== Restaurant methods =============================================================

    /**
     * Returns true if a restaurant with the same identity as {@code restaurant} exists in the address book.
     */
    boolean hasRestaurant(Restaurant restaurant);

    /**
     * Deletes the given restaurant.
     * The restaurant must exist in the address book.
     */
    void deleteRestaurant(Restaurant target);

    /**
     * Adds the given restaurant.
     * {@code restaurant} must not already exist in the address book.
     */
    void addRestaurant(Restaurant restaurant);

    /**
     * Replaces the given restaurant {@code target} with {@code editedRestaurant}.
     * {@code target} must exist in the restaurant database.
     * The restaurant identity of {@code editedRestaurant} must not be the same as another existing restaurant
     * in the restaurant database.
     */
    void setRestaurant(Restaurant target, Restaurant editedRestaurant);

    /** Returns an unmodifiable view of the filtered restaurant list */
    ObservableList<Restaurant> getFilteredRestaurantList();

    /**
     * Updates the filter of the filtered restaurant list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRestaurantList(Predicate<Restaurant> predicate);

    //=========== Deliveryman Methods =============================================================

    /** Returns an unmodifiable view of the filtered deliverymen list */
    ObservableList<Deliveryman> getFilteredDeliverymenList();

    /**
     * Returns true if a deliveryman with the same identity as {@code deliveryman} exists in the deliveryman book.
     */
    boolean hasDeliveryman(Deliveryman deliveryman);

    /**
     * Adds the given customer.
     * {@code customer} must not already exist in the address book.
     */
    void addDeliveryman(Deliveryman deliveryman);

    /**
     * Deletes the given customer.
     * The customer must exist in the address book.
     */
    void deleteDeliveryman(Deliveryman target);

    /**
     * Replaces the given deliveryman {@code target} with {@code editedDeliveryman}.
     * {@code target} must exist in the deliverymen database.
     * The deliveryman identity of {@code editedDeliveryman} must not be the same as another existing deliveryman
     * in the deliverymen database.
     */
    void setDeliveryman(Deliveryman target, Deliveryman editedDeliveryman);

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDeliverymenList(Predicate<Deliveryman> predicate);

    //=========== Order Methods =============================================================

    /**
     * Returns true if an order with the same identity as {@code order} exists in the address book.
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
}
