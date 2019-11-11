package seedu.deliverymans.model;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.deliverymans.commons.core.GuiSettings;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.logic.Context;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.database.CustomerDatabase;
import seedu.deliverymans.model.database.DeliverymenDatabase;
import seedu.deliverymans.model.database.OrderDatabase;
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderDatabase;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;
import seedu.deliverymans.model.database.RestaurantDatabase;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.deliveryman.deliverymanstatistics.StatisticsRecordCard;
import seedu.deliverymans.model.deliveryman.exceptions.InvalidStatusChangeException;
import seedu.deliverymans.model.deliveryman.exceptions.NoMoreAvailableDeliverymanException;
import seedu.deliverymans.model.deliveryman.exceptions.UnableToDeleteDeliveringDeliverymanException;
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
    private final FilteredList<Deliveryman> availableDeliverymen;
    private final FilteredList<Deliveryman> unavailableDeliverymen;
    private final FilteredList<Deliveryman> deliveringDeliverymen;
    private final FilteredList<Restaurant> filteredRestaurants;
    private final FilteredList<Restaurant> editingRestaurant;
    private final UndoHistory<Data> undoHistory;

    private Context context;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyCustomerDatabase customerDatabase,
                        ReadOnlyDeliverymenDatabase deliverymenDatabase,
                        ReadOnlyRestaurantDatabase restaurantDatabase,
                        ReadOnlyOrderDatabase orderBook,
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

        filteredRestaurants = new FilteredList<>(this.restaurantDatabase.getRestaurantList());
        editingRestaurant = new FilteredList<>(this.restaurantDatabase.getEditingRestaurantList());

        filteredDeliverymen = new FilteredList<>(this.deliverymenDatabase.getDeliverymenList());
        availableDeliverymen = new FilteredList<>(this.deliverymenDatabase.getAvailableDeliverymenList());
        unavailableDeliverymen = new FilteredList<>(this.deliverymenDatabase.getUnavailableDeliverymenList());
        deliveringDeliverymen = new FilteredList<>(this.deliverymenDatabase.getDeliveringDeliverymenList());

        filteredOrders = new FilteredList<>(this.orderDatabase.getOrderList());

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
        ObservableList<Order> orders = getFilteredOrderList();
        ArrayList<Order> ordersToDelete = new ArrayList<>();
        for (Order order : orders) {
            if (target.getUserName().equals(order.getCustomer()) && !order.isCompleted()) {
                ordersToDelete.add(order);
            }
        }
        int size = ordersToDelete.size();
        for (int i = 0; i < size; i++) {
            updateDeliverymanStatusAfterChangesToOrder(ordersToDelete.get(i).getDeliveryman());
            deleteOrder(ordersToDelete.get(i));
        }
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
    public ObservableList<Order> getCustomerOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        int count = 0;
        for (Order order : getFilteredOrderList()) {
            if (order.getCustomer().equals(customerDatabase.getCustomerOrders().getUserName())) {
                orders.add(order);
                count++;
            }
        }
        Customer customer = customerDatabase.getCustomerOrders();
        setCustomer(customer, customer.setNoOfOrders(count));
        ObservableList<Order> modelOrders = FXCollections.observableArrayList();
        modelOrders.addAll(orders);
        return modelOrders;
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
        if (getEditingRestaurantList().size() > 0 && target.equals(getEditingRestaurantList().get(0))) {
            setEditingRestaurant(editedRestaurant);
        }
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
    public void deleteDeliveryman(Deliveryman target) throws UnableToDeleteDeliveringDeliverymanException {
        deliverymenDatabase.removeDeliveryman(target);
    }

    @Override
    public void addDeliveryman(Deliveryman deliveryman) {
        deliverymenDatabase.addDeliveryman(deliveryman);
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
    }

    @Override
    public void switchDeliverymanStatus(Deliveryman deliveryman) throws InvalidStatusChangeException {
        requireNonNull(deliveryman);
        Deliveryman editedDeliveryman = deliverymenDatabase.switchDeliverymanStatus(deliveryman);
        if (editedDeliveryman.getStatus().getDescription().equals("AVAILABLE")) {
            signalNewAvailableDeliveryman();
        }
    }

    @Override
    public Name getOneAvailableDeliveryman() throws NoMoreAvailableDeliverymanException {
        return deliverymenDatabase.getAvailableDeliveryman();
    }

    @Override
    public void updateDeliverymanStatusAfterChangesToOrder(Name deliverymanName) {
        deliverymenDatabase.updateDeliverymanStatusAfterChangesToOrder(deliverymanName);
        signalNewAvailableDeliveryman();
    }

    @Override
    public StatisticsRecordCard getDeliverymenStatusStats() {
        return deliverymenDatabase.analyzeDeliverymenStatus();
    }

    @Override
    public void signalNewAvailableDeliveryman() {
        assignUnassignedOrder();
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
    public void setOrderDatabase(ReadOnlyOrderDatabase orderDatabase) {
        this.orderDatabase.resetData(orderDatabase);
    }

    @Override
    public ReadOnlyOrderDatabase getOrderDatabase() {
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

    @Override
    public Order getOrder(Name targetOrder) {
        Order orderToGet = null;
        for (Order order : getFilteredOrderList()) {
            if (order.getOrderName().equals(targetOrder)) {
                orderToGet = order;
            }
        }
        return orderToGet;
    }

    @Override
    public Name getFreeOrderName() {
        int n = 0;
        for (Order order : getFilteredOrderList()) {
            String orderNumber = order.getOrderName().fullName.split("\\s")[1];
            if (Integer.parseInt(orderNumber) >= n) {
                n = Integer.parseInt(orderNumber);
            }
        }
        n++;
        StringBuilder builder = new StringBuilder();
        builder.append("Order ")
                .append(n);
        return new Name(builder.toString());
    }

    @Override
    public void addOrderInCustomer(Order order) {
        Customer customer = null;
        for (Customer cust : getFilteredCustomerList()) {
            if (cust.getUserName().equals(order.getCustomer())) {
                customer = cust;
                break;
            }
        }
        Restaurant restaurant = null;
        for (Restaurant rest : getFilteredRestaurantList()) {
            if (rest.getName().equals(order.getRestaurant())) {
                restaurant = rest;
                break;
            }
        }
        setCustomer(customer, customer.addOrder(restaurant.getTags()));
    }

    @Override
    public void deleteOrderInCustomer(Order order) {
        Customer customer = null;
        for (Customer cust : getFilteredCustomerList()) {
            if (cust.getUserName().equals(order.getCustomer())) {
                customer = cust;
                break;
            }
        }
        Restaurant restaurant = null;
        for (Restaurant rest : getFilteredRestaurantList()) {
            if (rest.getName().equals(order.getRestaurant())) {
                restaurant = rest;
                break;
            }
        }
        setCustomer(customer, customer.deleteOrder(restaurant.getTags()));
    }

    @Override
    public void assignUnassignedOrder() {
        LinkedList<Order> sortedList = getFilteredOrderList().stream().sorted((o1, o2)
            -> o1.getOrderName().fullName.compareToIgnoreCase(o2.getOrderName().fullName))
                .collect(Collectors.toCollection(LinkedList::new));
        for (Order order : sortedList) {
            if (order.getDeliveryman().fullName.equalsIgnoreCase("Unassigned")) {
                try {
                    Name newDeliveryman = getOneAvailableDeliveryman();
                    Order assignedOrder = new Order.OrderBuilder().setOrderName(order.getOrderName())
                            .setCustomer(order.getCustomer()).setRestaurant(order.getRestaurant())
                            .setFood(order.getFoodList()).setDeliveryman(newDeliveryman).completeOrder();
                    updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
                    setOrder(order, assignedOrder);
                } catch (NoMoreAvailableDeliverymanException e) {
                    break;
                }
            }
        }
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

    @Override
    public List<Optional<String>> getUndoList() {
        int currPos = undoHistory.getCurrentPosition();
        List<UndoHistory<Data>.State> states = undoHistory.getStateList();
        List<Optional<String>> list = new ArrayList<>();
        for (int i = 0; i < states.size(); i++) {
            if (i < currPos) {
                list.add(Optional.of(states.get(i).getSubsequentCause()));
            } else if (i > currPos) {
                list.add(Optional.of(states.get(i).getCause()));
            } else {
                list.add(Optional.empty());
            }
        }
        return list;
    }

    @Override
    public int undoSize() {
        return undoHistory.size();
    }

    @Override
    public String undoTill(int position) {
        int currPos = undoHistory.getCurrentPosition();
        if (position == currPos) {
            return "Already at this position";
        }
        UndoHistory<Data>.State state = undoHistory.undoTill(position);
        setData(state.getData());
        if (position < currPos) {
            return "Undid till: " + state.getSubsequentCause();
        } else {
            return "Redid till: " + state.getCause();
        }
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
    public ObservableList<Deliveryman> getAvailableMenList() {
        return availableDeliverymen;
    }

    @Override
    public ObservableList<Deliveryman> getUnavailableMenList() {
        return unavailableDeliverymen;
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
