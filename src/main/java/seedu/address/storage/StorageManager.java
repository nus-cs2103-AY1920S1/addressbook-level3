package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.ReadOnlyUserList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.UniqueRecordList;
import sugarmummy.recmfood.model.UniqueFoodList;
import sugarmummy.recmfood.storage.JsonFoodListStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserListStorage userListStorage;
    private UserPrefsStorage userPrefsStorage;
    private JsonFoodListStorage jsonFoodListStorage;
    private JsonRecordListStorage jsonRecordListStorage;
    private JsonCalendarStorage calendarStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          UserListStorage userListStorage, JsonFoodListStorage jsonFoodListStorage,
                          JsonRecordListStorage jsonRecordListStorage, JsonCalendarStorage calendarStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userListStorage = userListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.jsonFoodListStorage = jsonFoodListStorage;
        this.jsonRecordListStorage = jsonRecordListStorage;
        this.calendarStorage = calendarStorage;
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

    // ================ FoodList methods ==============================
    @Override
    public Path getFoodListFilePath() {
        return jsonFoodListStorage.getFilePath();
    }

    @Override
    public Optional<UniqueFoodList> readFoodList() throws DataConversionException, IOException {
        return readFoodList(jsonFoodListStorage.getFilePath());
    }

    @Override
    public Optional<UniqueFoodList> readFoodList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return jsonFoodListStorage.read(filePath);
    }

    @Override
    public void saveFoodList(UniqueFoodList foodList) throws IOException {
        saveFoodList(foodList, jsonFoodListStorage.getFilePath());
    }

    @Override
    public void saveFoodList(UniqueFoodList foodList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        jsonFoodListStorage.save(foodList, filePath);
    }

    // ================ RecordList methods ==============================
    @Override
    public Path getRecordListFilePath() {
        return jsonRecordListStorage.getFilePath();
    }

    @Override
    public Optional<UniqueRecordList> readRecordList() throws DataConversionException, IOException {
        return readRecordList(jsonRecordListStorage.getFilePath());
    }

    @Override
    public Optional<UniqueRecordList> readRecordList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return jsonRecordListStorage.read(filePath);
    }

    @Override
    public void saveRecordList(UniqueRecordList recordList) throws IOException {
        saveRecordList(recordList, jsonRecordListStorage.getFilePath());
    }

    @Override
    public void saveRecordList(UniqueRecordList recordList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        jsonRecordListStorage.save(recordList, filePath);
    }

    // ================ UserList methods ==============================
    @Override
    public Path getUserListFilePath() {
        return userListStorage.getUserListFilePath();
    }

    @Override
    public Optional<ReadOnlyUserList> readUserList() throws DataConversionException, IOException {
        return readUserList(userListStorage.getUserListFilePath());
    }

    @Override
    public Optional<ReadOnlyUserList> readUserList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userListStorage.readUserList(filePath);
    }

    @Override
    public void saveUserList(ReadOnlyUserList userList) throws IOException {
        saveUserList(userList, userListStorage.getUserListFilePath());
    }

    @Override
    public void saveUserList(ReadOnlyUserList userList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userListStorage.saveUserList(userList, filePath);
    }

    @Override
    public List<Map<String, String>> getListOfFieldsContainingInvalidReferences() {
        return userListStorage.getListOfFieldsContainingInvalidReferences();
    }

    // ===================== Calendar ======================
    @Override
    public Path getEventFilePath() {
        return calendarStorage.getEventFilePath();
    }

    @Override
    public Path getReminderFilePath() {
        return calendarStorage.getReminderFilePath();
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException {
        return readCalendar(calendarStorage.getEventFilePath(), calendarStorage.getReminderFilePath());
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar(Path eventFilePath, Path reminderFilePath)
        throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + eventFilePath + " and " + reminderFilePath);
        return calendarStorage.readCalendar(eventFilePath, reminderFilePath);
    }

    @Override
    public void saveCalendar(ReadOnlyCalendar calendar) throws IOException {
        saveCalendar(calendar, calendarStorage.getEventFilePath(), calendarStorage.getReminderFilePath());
    }

    @Override
    public void saveCalendar(ReadOnlyCalendar calendar, Path eventFilePath, Path reminderFilePath) throws IOException {
        logger.fine("Attempting to write to data file: " + eventFilePath + " and " + reminderFilePath);
        calendarStorage.saveCalendar(calendar, eventFilePath, reminderFilePath);
    }
}
