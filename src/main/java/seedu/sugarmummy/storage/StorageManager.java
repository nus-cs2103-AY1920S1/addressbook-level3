package seedu.sugarmummy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.commons.exceptions.DataConversionException;
import seedu.sugarmummy.model.ReadOnlyUserPrefs;
import seedu.sugarmummy.model.UserPrefs;
import seedu.sugarmummy.model.biography.ReadOnlyUserList;
import seedu.sugarmummy.model.calendar.ReadOnlyCalendar;
import seedu.sugarmummy.model.recmf.UniqueFoodList;
import seedu.sugarmummy.model.records.UniqueRecordList;
import seedu.sugarmummy.storage.biography.UserListStorage;
import seedu.sugarmummy.storage.calendar.JsonCalendarStorage;
import seedu.sugarmummy.storage.recmf.JsonFoodListStorage;
import seedu.sugarmummy.storage.records.JsonRecordListStorage;

/**
 * Manages storage of SugarMummy data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserListStorage userListStorage;
    private UserPrefsStorage userPrefsStorage;
    private JsonFoodListStorage jsonFoodListStorage;
    private JsonRecordListStorage jsonRecordListStorage;
    private JsonCalendarStorage calendarStorage;


    public StorageManager(UserPrefsStorage userPrefsStorage,
                          UserListStorage userListStorage, JsonFoodListStorage jsonFoodListStorage,
                          JsonRecordListStorage jsonRecordListStorage, JsonCalendarStorage calendarStorage) {
        super();
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
