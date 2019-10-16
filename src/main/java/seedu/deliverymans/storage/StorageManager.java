package seedu.deliverymans.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.commons.exceptions.DataConversionException;
import seedu.deliverymans.model.ReadOnlyUserPrefs;
import seedu.deliverymans.model.UserPrefs;
import seedu.deliverymans.model.addressbook.ReadOnlyAddressBook;
import seedu.deliverymans.model.database.DeliverymenDatabase;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderBook;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;
import seedu.deliverymans.storage.deliveryman.DeliverymenDatabaseStorage;
import seedu.deliverymans.storage.restaurant.RestaurantDatabaseStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private DeliverymenDatabaseStorage deliverymenDatabaseStorage;
    private RestaurantDatabaseStorage restaurantDatabaseStorage;
    private OrderBookStorage orderBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, RestaurantDatabaseStorage restaurantDatabaseStorage,
                          OrderBookStorage orderBookStorage, DeliverymenDatabaseStorage deliverymenDatabaseStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.restaurantDatabaseStorage = restaurantDatabaseStorage;
        this.orderBookStorage = orderBookStorage;
        this.deliverymenDatabaseStorage = deliverymenDatabaseStorage;
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

    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
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
        return orderBookStorage.getOrderBookFilePath();
    }

    @Override
    public Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException {
        return readOrderBook(orderBookStorage.getOrderBookFilePath());
    }

    @Override
    public Optional<ReadOnlyOrderBook> readOrderBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return orderBookStorage.readOrderBook(filePath);
    }

    @Override
    public void saveOrderBook(ReadOnlyOrderBook orderBook) throws IOException {
        saveOrderBook(orderBook, orderBookStorage.getOrderBookFilePath());
    }

    @Override
    public void saveOrderBook(ReadOnlyOrderBook orderBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        orderBookStorage.saveOrderBook(orderBook, filePath);
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
