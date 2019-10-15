package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDiary;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of DiaryRecords data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private DiaryStorage diaryStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(DiaryStorage diaryStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.diaryStorage = diaryStorage;
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


    // ================ DiaryRecords methods ==============================

    @Override
    public Path getDiaryFilePath() {
        return diaryStorage.getDiaryFilePath();
    }

    @Override
    public Optional<ReadOnlyDiary> readDiary() throws DataConversionException, IOException {
        return readDiary(diaryStorage.getDiaryFilePath());
    }

    @Override
    public Optional<ReadOnlyDiary> readDiary(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return diaryStorage.readDiary(filePath);
    }

    @Override
    public void saveDiary(ReadOnlyDiary diary) throws IOException {
        saveDiary(diary, diaryStorage.getDiaryFilePath());
    }

    @Override
    public void saveDiary(ReadOnlyDiary diary, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        diaryStorage.saveDiary(diary, filePath);
    }

}
