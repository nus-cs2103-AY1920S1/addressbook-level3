package seedu.deliverymans.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
 * API of the Storage component
 */
public interface Storage extends CustomerDatabaseStorage, DeliverymenDatabaseStorage,
        RestaurantDatabaseStorage, OrderDatabaseStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCustomerDatabaseFilePath();

    @Override
    Path getDeliverymenDatabaseFilePath();

    @Override
    Path getRestaurantDatabaseFilePath();

    @Override
    Optional<ReadOnlyDeliverymenDatabase> readDeliverymenDatabase() throws DataConversionException, IOException;

    @Override
    void saveDeliverymenDatabase(ReadOnlyDeliverymenDatabase deliverymenDatabase) throws IOException;

    Optional<ReadOnlyCustomerDatabase> readCustomerDatabase() throws DataConversionException, IOException;

    @Override
    void saveCustomerDatabase(ReadOnlyCustomerDatabase customerDatabase) throws IOException;

    @Override
    Optional<ReadOnlyRestaurantDatabase> readRestaurantDatabase() throws DataConversionException, IOException;

    @Override
    void saveRestaurantDatabase(ReadOnlyRestaurantDatabase addressBook) throws IOException;

    @Override
    Optional<ReadOnlyOrderDatabase> readOrderBook() throws DataConversionException, IOException;

    @Override
    void saveOrderDatabase(ReadOnlyOrderDatabase orderBook) throws IOException;
}
