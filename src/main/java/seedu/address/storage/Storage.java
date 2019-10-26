package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, CustomerBookStorage, PhoneBookStorage, ScheduleBookStorage,
        OrderBookStorage, ArchivedOrderBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Path getCustomerBookFilePath();

    @Override
    Optional<ReadOnlyDataBook<Customer>> readCustomerBook() throws DataConversionException, IOException;

    @Override
    void saveCustomerBook(ReadOnlyDataBook<Customer> customerBook) throws IOException;

    @Override
    Path getPhoneBookFilePath();

    @Override
    Optional<ReadOnlyDataBook<Phone>> readPhoneBook() throws DataConversionException, IOException;

    @Override
    void savePhoneBook(ReadOnlyDataBook<Phone> phoneBook) throws IOException;

    @Override
    Path getScheduleBookFilePath();

    @Override
    Optional<ReadOnlyDataBook<Schedule>> readScheduleBook() throws DataConversionException, IOException;

    @Override
    void saveScheduleBook(ReadOnlyDataBook<Schedule> scheduleBook) throws IOException;

    @Override
    Path getOrderBookFilePath();

    @Override
    Optional<ReadOnlyDataBook<Order>> readOrderBook() throws DataConversionException, IOException;

    @Override
    void saveOrderBook(ReadOnlyDataBook<Order> orderBook) throws IOException;

    @Override
    Path getArchivedOrderBookFilePath();

    @Override
    Optional<ReadOnlyDataBook<Order>> readArchivedOrderBook() throws DataConversionException, IOException;

    @Override
    void saveArchivedOrderBook(ReadOnlyDataBook<Order> orderBook) throws IOException;
}
