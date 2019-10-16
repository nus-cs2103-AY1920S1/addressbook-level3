package seedu.deliverymans.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.deliverymans.commons.exceptions.DataConversionException;
import seedu.deliverymans.model.ReadOnlyUserPrefs;
import seedu.deliverymans.model.UserPrefs;
import seedu.deliverymans.model.addressbook.ReadOnlyAddressBook;
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderBook;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;
import seedu.deliverymans.storage.customer.CustomerDatabaseStorage;
import seedu.deliverymans.storage.restaurant.RestaurantDatabaseStorage;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, CustomerDatabaseStorage, RestaurantDatabaseStorage,
        OrderBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Path getCustomerDatabaseFilePath();

    @Override
    Path getRestaurantDatabaseFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Optional<ReadOnlyCustomerDatabase> readCustomerDatabase() throws DataConversionException, IOException;

    @Override
    void saveCustomerDatabase(ReadOnlyCustomerDatabase customerDatabase) throws IOException;

    @Override
    Optional<ReadOnlyRestaurantDatabase> readRestaurantDatabase() throws DataConversionException, IOException;

    @Override
    void saveRestaurantDatabase(ReadOnlyRestaurantDatabase addressBook) throws IOException;

    @Override
    Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException;

    @Override
    void saveOrderBook(ReadOnlyOrderBook orderBook) throws IOException;
}
