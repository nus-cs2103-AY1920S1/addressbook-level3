package seedu.deliverymans.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.commons.exceptions.DataConversionException;
import seedu.deliverymans.model.ReadOnlyUserPrefs;
import seedu.deliverymans.model.UserPrefs;
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderDatabase;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;
import seedu.deliverymans.storage.customer.CustomerDatabaseStorage;
import seedu.deliverymans.storage.deliveryman.DeliverymenDatabaseStorage;
import seedu.deliverymans.storage.order.OrderDatabaseStorage;
import seedu.deliverymans.storage.restaurant.RestaurantDatabaseStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CustomerDatabaseStorage customerDatabaseStorage;
    private DeliverymenDatabaseStorage deliverymenDatabaseStorage;
    private RestaurantDatabaseStorage restaurantDatabaseStorage;
    private OrderDatabaseStorage orderDatabaseStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(CustomerDatabaseStorage customerDatabaseStorage,
                          DeliverymenDatabaseStorage deliverymenDatabaseStorage,
                          RestaurantDatabaseStorage restaurantDatabaseStorage,
                          OrderDatabaseStorage orderDatabaseStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.customerDatabaseStorage = customerDatabaseStorage;
        this.deliverymenDatabaseStorage = deliverymenDatabaseStorage;
        this.restaurantDatabaseStorage = restaurantDatabaseStorage;
        this.orderDatabaseStorage = orderDatabaseStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ CustomerDatabase methods ==============================

    @Override
    public Path getCustomerDatabaseFilePath() {
        return customerDatabaseStorage.getCustomerDatabaseFilePath();
    }

    @Override
    public Optional<ReadOnlyCustomerDatabase> readCustomerDatabase() throws DataConversionException, IOException {
        return readCustomerDatabase(customerDatabaseStorage.getCustomerDatabaseFilePath());
    }

    @Override
    public Optional<ReadOnlyCustomerDatabase> readCustomerDatabase(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return customerDatabaseStorage.readCustomerDatabase(filePath);
    }

    @Override
    public void saveCustomerDatabase(ReadOnlyCustomerDatabase customerDatabase) throws IOException {
        saveCustomerDatabase(customerDatabase, customerDatabaseStorage.getCustomerDatabaseFilePath());
    }

    @Override
    public void saveCustomerDatabase(ReadOnlyCustomerDatabase customerDatabase, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        customerDatabaseStorage.saveCustomerDatabase(customerDatabase, filePath);
    }


    // ================ RestaurantDatabase methods ==============================

    @Override
    public Path getRestaurantDatabaseFilePath() {
        return restaurantDatabaseStorage.getRestaurantDatabaseFilePath();
    }

    @Override
    public Optional<ReadOnlyRestaurantDatabase> readRestaurantDatabase() throws DataConversionException, IOException {
        return readRestaurantDatabase(restaurantDatabaseStorage.getRestaurantDatabaseFilePath());
    }

    @Override
    public Optional<ReadOnlyRestaurantDatabase> readRestaurantDatabase(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return restaurantDatabaseStorage.readRestaurantDatabase(filePath);
    }

    @Override
    public void saveRestaurantDatabase(ReadOnlyRestaurantDatabase restaurantDatabase) throws IOException {
        saveRestaurantDatabase(restaurantDatabase, restaurantDatabaseStorage.getRestaurantDatabaseFilePath());
    }

    @Override
    public void saveRestaurantDatabase(ReadOnlyRestaurantDatabase restaurantDatabase, Path filePath)
            throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        restaurantDatabaseStorage.saveRestaurantDatabase(restaurantDatabase, filePath);
    }

    // ================ OrderBook methods ==============================

    @Override
    public Path getOrderBookFilePath() {
        return orderDatabaseStorage.getOrderBookFilePath();
    }

    @Override
    public Optional<ReadOnlyOrderDatabase> readOrderBook() throws DataConversionException, IOException {
        return readOrderBook(orderDatabaseStorage.getOrderBookFilePath());
    }

    @Override
    public Optional<ReadOnlyOrderDatabase> readOrderBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return orderDatabaseStorage.readOrderBook(filePath);
    }

    @Override
    public void saveOrderDatabase(ReadOnlyOrderDatabase orderBook) throws IOException {
        saveOrderDatabase(orderBook, orderDatabaseStorage.getOrderBookFilePath());
    }

    @Override
    public void saveOrderDatabase(ReadOnlyOrderDatabase orderBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        orderDatabaseStorage.saveOrderDatabase(orderBook, filePath);
    }

    // ================ DeliverymenDatabase methods ==============================

    @Override
    public Path getDeliverymenDatabaseFilePath() {
        return deliverymenDatabaseStorage.getDeliverymenDatabaseFilePath();
    }

    @Override
    public Optional<ReadOnlyDeliverymenDatabase> readDeliverymenDatabase() throws DataConversionException, IOException {
        return readDeliverymenDatabase(deliverymenDatabaseStorage.getDeliverymenDatabaseFilePath());
    }

    @Override
    public Optional<ReadOnlyDeliverymenDatabase> readDeliverymenDatabase(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return deliverymenDatabaseStorage.readDeliverymenDatabase(filePath);
    }

    @Override
    public void saveDeliverymenDatabase(ReadOnlyDeliverymenDatabase deliverymenDatabase) throws IOException {
        saveDeliverymenDatabase(deliverymenDatabase, deliverymenDatabaseStorage.getDeliverymenDatabaseFilePath());
    }

    @Override
    public void saveDeliverymenDatabase(ReadOnlyDeliverymenDatabase deliverymenDatabase, Path filePath)
            throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        deliverymenDatabaseStorage.saveDeliverymenDatabase(deliverymenDatabase, filePath);
    }
}
