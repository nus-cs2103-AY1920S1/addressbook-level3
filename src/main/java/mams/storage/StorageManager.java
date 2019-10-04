package mams.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import mams.commons.core.LogsCenter;
import mams.commons.exceptions.DataConversionException;
import mams.model.ReadOnlyMams;
import mams.model.ReadOnlyUserPrefs;
import mams.model.UserPrefs;

/**
 * Manages storage of Mams data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MamsStorage mamsStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(MamsStorage mamsStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.mamsStorage = mamsStorage;
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


    // ================ Mams methods ==============================

    @Override
    public Path getMamsFilePath() {
        return mamsStorage.getMamsFilePath();
    }

    @Override
    public Optional<ReadOnlyMams> readMams() throws DataConversionException, IOException {
        return readMams(mamsStorage.getMamsFilePath());
    }

    @Override
    public Optional<ReadOnlyMams> readMams(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return mamsStorage.readMams(filePath);
    }

    @Override
    public void saveMams(ReadOnlyMams mams) throws IOException {
        saveMams(mams, mamsStorage.getMamsFilePath());
    }

    @Override
    public void saveMams(ReadOnlyMams mams, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        mamsStorage.saveMams(mams, filePath);
    }

}
