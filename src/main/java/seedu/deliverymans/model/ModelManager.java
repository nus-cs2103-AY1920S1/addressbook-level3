package seedu.deliverymans.model;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.deliverymans.commons.core.GuiSettings;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.logic.parser.universal.Context;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.database.CustomerDatabase;
import seedu.deliverymans.model.database.DeliverymenDatabase;
import seedu.deliverymans.model.database.OrderDatabase;
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderBook;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;
import seedu.deliverymans.model.database.RestaurantDatabase;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.deliveryman.deliverymanstatistics.DeliveryRecord;
import seedu.deliverymans.model.deliveryman.exceptions.InvalidStatusChangeException;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final OrderDatabase orderDatabase;
    private final CustomerDatabase customerDatabase;
    private final DeliverymenDatabase deliverymenDatabase;
    private final RestaurantDatabase restaurantDatabase;

    private final UserPrefs userPrefs;

    private final FilteredList<Order> filteredOrders;
    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Deliveryman> filteredDeliverymen;
    private final FilteredList<Deliveryman> statusSortedDeliverymen;
    private final FilteredList<Deliveryman> availableDeliverymen;
    private final FilteredList<Deliveryman> unavailableDeliverymen;
    private final FilteredList<Deliveryman> deliveringDeliverymen;
    private final FilteredList<Restaurant> filteredRestaurants;
    private final FilteredList<Restaurant> editingRestaurant;
    private DeliveryRecord deliveryRecordPlaceholder;
    private final UndoHistory<Data> undoHistory;

    private Context context;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyCustomerDatabase customerDatabase,
                        ReadOnlyDeliverymenDatabase deliverymenDatabase,
                        ReadOnlyRestaurantDatabase restaurantDatabase,
                        ReadOnlyOrderBook orderBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(customerDatabase, deliverymenDatabase, restaurantDatabase, orderBook, userPrefs);

        logger.fine("Initializing with customer database: " + customerDatabase
                + " and deliverymen database: " + deliverymenDatabase
                + " and restaurant database: " + restaurantDatabase
                + " and order book: " + orderBook
                + " and user prefs " + userPrefs);

        this.customerDatabase = new CustomerDatabase(customerDatabase);
        this.deliverymenDatabase = new DeliverymenDatabase(deliverymenDatabase);
        this.restaurantDatabase = new RestaurantDatabase(restaurantDatabase);
        this.orderDatabase = new OrderDatabase(orderBook);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredCustomers = new FilteredList<>(this.customerDatabase.getCustomerList());
        filteredDeliverymen = new FilteredList<>(this.deliverymenDatabase.getDeliverymenList());
        statusSortedDeliverymen = new FilteredList<>(this.deliverymenDatabase.getStatusSortedDeliverymenList());
        availableDeliverymen = new FilteredList<>(this.deliverymenDatabase.getAvailableDeliverymenList());
        unavailableDeliverymen = new FilteredList<>(this.deliverymenDatabase.getUnavailableDeliverymenList());
        deliveringDeliverymen = new FilteredList<>(this.deliverymenDatabase.getDeliveringDeliverymenList());
        deliveryRecordPlaceholder = new DeliveryRecord(new Name("hey"));
        filteredRestaurants = new FilteredList<>(this.restaurantDatabase.getRestaurantList());
        filteredOrders = new FilteredList<>(this.orderDatabase.getOrderList());
        editingRestaurant = new FilteredList<>(this.restaurantDatabase.getEditingRestaurantList());

        undoHistory = new UndoHistory<>(new Data(this));
    }

    public ModelManager() {
        this(new CustomerDatabase(), new DeliverymenDatabase(),
                new RestaurantDatabase(), new OrderDatabase(), new UserPrefs());
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

    //=========== Customer Methods =============================================================
    @Override
    public Path getCustomerDatabaseFilePath() {
        return userPrefs.getCustomerDatabaseFilePath();
    }

    @Override
    public void setCustomerDatabaseFilePath(Path customerDatabaseFilePath) {
        requireNonNull(customerDatabaseFilePath);
        userPrefs.setCustomerDatabaseFilePath(customerDatabaseFilePath);
    }

    @Override
    public void setCustomerDatabase(ReadOnlyCustomerDatabase customerDatabase) {
        this.customerDatabase.resetData(customerDatabase);
    }

    @Override
    public ReadOnlyCustomerDatabase getCustomerDatabase() {
        return customerDatabase;
    }

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

    @Override
    public void setCustomerOrders(Customer customer) {
        requireAllNonNull(customer);
        customerDatabase.setCustomerOrders(customer);
    }

    @Override
    public Customer getCustomerOrders() {
        return customerDatabase.getCustomerOrders();
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

    @Override
    public void setEditingRestaurant(Restaurant editingRestaurant) {
        requireAllNonNull(editingRestaurant);
        ArrayList<Restaurant> list = new ArrayList<Restaurant>();
        list.add(editingRestaurant);
        restaurantDatabase.setEditingRestaurant(list);
    }


    //=========== Deliveryman Methods =============================================================

    @Override
    public Path getDeliverymenDatabaseFilePath() {
        return userPrefs.getDeliverymenDatabaseFilePath();
    }

    @Override
    public void setDeliverymenDatabaseFilePath(Path deliverymenDatabaseFilePath) {
        requireNonNull(deliverymenDatabaseFilePath);
        userPrefs.setDeliverymenDatabaseFilePath(deliverymenDatabaseFilePath);
    }

    @Override
    public void setDeliverymenDatabase(ReadOnlyDeliverymenDatabase deliverymenDatabase) {
        this.deliverymenDatabase.resetData(deliverymenDatabase);
    }

    @Override
    public ReadOnlyDeliverymenDatabase getDeliverymenDatabase() {
        return deliverymenDatabase;
    }

    @Override
    public void deleteDeliveryman(Deliveryman target) {
        deliverymenDatabase.removeDeliveryman(target);
        deliverymenDatabase.updateStatusList();
    }

    @Override
    public void addDeliveryman(Deliveryman deliveryman) {
        deliverymenDatabase.addDeliveryman(deliveryman);
        updateAvailableDeliverymenList(PREDICATE_SHOW_ALL_DELIVERYMEN);
    }

    @Override
    public boolean hasDeliveryman(Deliveryman deliveryman) {
        requireNonNull(deliveryman);
        return deliverymenDatabase.hasDeliveryman(deliveryman);
    }

    @Override
    public void setDeliveryman(Deliveryman target, Deliveryman editedDeliveryman) {
        requireAllNonNull(target, editedDeliveryman);
        deliverymenDatabase.setDeliveryman(target, editedDeliveryman);
        deliverymenDatabase.updateStatusList();
        updateAvailableDeliverymenList(PREDICATE_SHOW_ALL_DELIVERYMEN);
    }

    @Override
    public void switchDeliverymanStatus(Deliveryman deliveryman) throws InvalidStatusChangeException {
        requireNonNull(deliveryman);
        deliverymenDatabase.switchDeliverymanStatus(deliveryman);
        deliverymenDatabase.updateStatusList();
    }

    @Override
    public DeliveryRecord getDeliverymanRecord(Deliveryman deliveryman) {
        return deliverymenDatabase.getDeliverymanRecord(deliveryman);
    }

    @Override
    public void setToShowDeliverymanRecord(DeliveryRecord record) {
        deliveryRecordPlaceholder = record;
    }

    @Override
    public void showAvailableDeliverymen() {
        deliverymenDatabase.resetAvailableList();
    }

    @Override
    public Name getOneAvailableDeliveryman() {
        return deliverymenDatabase.getAvailableDeliveryman();
    }

    @Override
    public void updateDeliverymanStatusAfterCompletingOrder(Deliveryman deliveryman) {
        deliverymenDatabase.updateDeliverymanStatusAfterCompletingOrder(deliveryman);
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
    public void setOrderDatabase(ReadOnlyOrderBook orderDatabase) {
        this.orderDatabase.resetData(orderDatabase);
    }

    @Override
    public ReadOnlyOrderBook getOrderDatabase() {
        return orderDatabase;
    }

    @Override
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orderDatabase.hasOrder(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderDatabase.removeOrder(order);
    }

    @Override
    public void addOrder(Order order) {
        orderDatabase.addOrder(order);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        orderDatabase.setOrder(target, editedOrder);
    }

    //=========== Undo ================================================================================

    @Override
    public void notifyChange(String commandText) {
        undoHistory.notifyChange(commandText, new Data(this));
    }

    @Override
    public boolean hasUndo() {
        return undoHistory.hasUndo();
    }

    @Override
    public boolean hasRedo() {
        return undoHistory.hasRedo();
    }

    @Override
    public String undo() {
        UndoHistory<Data>.State state = undoHistory.undo();
        setData(state.getData());
        return state.getSubsequentCause();
    }

    @Override
    public String redo() {
        UndoHistory<Data>.State state = undoHistory.redo();
        setData(state.getData());
        return state.getCause();
    }

    private void setData(Data data) {
        setCustomerDatabase(data.customerDatabase);
        setDeliverymenDatabase(data.deliverymenDatabase);
        setRestaurantDatabase(data.restaurantDatabase);
        setOrderDatabase(data.orderDatabase);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Customer} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return filteredCustomers;
    }

    @Override
    public ObservableList<Deliveryman> getFilteredDeliverymenList() {
        return filteredDeliverymen;
    }

    @Override
    public ObservableList<Deliveryman> getStatusSortedList() {
        return statusSortedDeliverymen;
    }

    @Override
    public ObservableList<Deliveryman> getAvailableMenList() {
        return availableDeliverymen;
    }

    @Override
    public ObservableList<Deliveryman> getUnavailableMenList() {
        return unavailableDeliverymen;
    }

    @Override
    public DeliveryRecord getDeliverymanRecordPlaceholder() {
        return deliveryRecordPlaceholder;
    }

    @Override
    public ObservableList<Deliveryman> getDeliveringMenList() {
        return deliveringDeliverymen;
    }

    @Override
    public ObservableList<Restaurant> getFilteredRestaurantList() {
        return filteredRestaurants;
    }

    @Override
    public ObservableList<Restaurant> getEditingRestaurantList() {
        return editingRestaurant;
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
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
    public void updateAvailableDeliverymenList(Predicate<Deliveryman> predicate) {
        requireNonNull(predicate);
        availableDeliverymen.setPredicate(predicate);
    }

    @Override
    public void updateFilteredDeliverymenList(Predicate<Deliveryman> predicate) {
        requireNonNull(predicate);
        filteredDeliverymen.setPredicate(predicate);
    }

    @Override
    public void updateFilteredRestaurantList(Predicate<Restaurant> predicate) {
        requireNonNull(predicate);
        filteredRestaurants.setPredicate(predicate);
    }

    @Override
    public void updateEditingRestaurantList(Predicate<Restaurant> predicate) {
        requireNonNull(predicate);
        editingRestaurant.setPredicate(predicate);
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
        return userPrefs.equals(other.userPrefs)
                && filteredCustomers.equals(other.filteredCustomers);
    }

    /**
     * Wrapper class for data stored in a model.
     */
    public static class Data {
        private final CustomerDatabase customerDatabase;
        private final DeliverymenDatabase deliverymenDatabase;
        private final RestaurantDatabase restaurantDatabase;
        private final OrderDatabase orderDatabase;

        public Data(Model model) {
            customerDatabase = new CustomerDatabase(model.getCustomerDatabase());
            deliverymenDatabase = new DeliverymenDatabase(model.getDeliverymenDatabase());
            restaurantDatabase = new RestaurantDatabase(model.getRestaurantDatabase());
            orderDatabase = new OrderDatabase(model.getOrderDatabase());
        }

        @Override
        public boolean equals(Object obj) {
            // short circuit if same object
            if (obj == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(obj instanceof Data)) {
                return false;
            }

            // state check
            Data other = (Data) obj;
            return customerDatabase.equals(other.customerDatabase)
                    && deliverymenDatabase.equals(other.deliverymenDatabase)
                    && restaurantDatabase.equals(other.restaurantDatabase)
                    && orderDatabase.equals(other.orderDatabase);
        }
    }
}
