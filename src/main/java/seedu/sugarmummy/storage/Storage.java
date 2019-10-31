package seedu.sugarmummy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.sugarmummy.commons.exceptions.DataConversionException;
import seedu.sugarmummy.model.ReadOnlyCalendar;
import seedu.sugarmummy.model.ReadOnlyUserList;
import seedu.sugarmummy.model.ReadOnlyUserPrefs;
import seedu.sugarmummy.model.UserPrefs;
import seedu.sugarmummy.model.record.UniqueRecordList;
import sugarmummy.recmfood.model.UniqueFoodList;

/**
 * API of the Storage component
 */
public interface Storage extends UserListStorage, UserPrefsStorage, CalendarStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    Path getFoodListFilePath();

    Optional<UniqueFoodList> readFoodList() throws DataConversionException, IOException;

    Optional<UniqueFoodList> readFoodList(Path filePath) throws DataConversionException, IOException;

    void saveFoodList(UniqueFoodList foodList) throws IOException;

    void saveFoodList(UniqueFoodList foodList, Path filePath) throws IOException;

    Path getRecordListFilePath();

    Optional<UniqueRecordList> readRecordList() throws DataConversionException, IOException;

    Optional<UniqueRecordList> readRecordList(Path filePath) throws DataConversionException, IOException;

    void saveRecordList(UniqueRecordList recordList) throws IOException;

    void saveRecordList(UniqueRecordList recordList, Path filePath) throws IOException;

    Path getEventFilePath();

    Path getReminderFilePath();

    Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException;

    void saveCalendar(ReadOnlyCalendar calendar) throws IOException;

    // ================ UserList methods ==============================
    @Override
    Path getUserListFilePath();

    @Override
    Optional<ReadOnlyUserList> readUserList() throws DataConversionException, IOException;

    @Override
    void saveUserList(ReadOnlyUserList userList) throws IOException;

    @Override
    List<Map<String, String>> getListOfFieldsContainingInvalidReferences();

}
