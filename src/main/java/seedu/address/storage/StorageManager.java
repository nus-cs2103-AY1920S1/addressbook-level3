package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyMooLah;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of MooLah data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MooLahStorage mooLahStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(MooLahStorage mooLahStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.mooLahStorage = mooLahStorage;
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


    // ================ MooLah methods ==============================

    @Override
    public Path getMooLahFilePath() {
        return mooLahStorage.getMooLahFilePath();
    }

    @Override
    public Optional<ReadOnlyMooLah> readMooLah() throws DataConversionException, IOException {
        return readMooLah(mooLahStorage.getMooLahFilePath());
    }

    @Override
    public Optional<ReadOnlyMooLah> readMooLah(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return mooLahStorage.readMooLah(filePath);
    }

    @Override
    public void saveMooLah(ReadOnlyMooLah mooLah) throws IOException {
        saveMooLah(mooLah, mooLahStorage.getMooLahFilePath());
    }

    @Override
    public void saveMooLah(ReadOnlyMooLah mooLah, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        mooLahStorage.saveMooLah(mooLah, filePath);
    }

}
