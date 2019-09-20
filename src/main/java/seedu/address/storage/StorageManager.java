package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAlgoBase;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AlgoBase data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AlgoBaseStorage algoBaseStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AlgoBaseStorage algoBaseStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.algoBaseStorage = algoBaseStorage;
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


    // ================ AlgoBase methods ==============================

    @Override
    public Path getAlgoBaseFilePath() {
        return algoBaseStorage.getAlgoBaseFilePath();
    }

    @Override
    public Optional<ReadOnlyAlgoBase> readAlgoBase() throws DataConversionException, IOException {
        return readAlgoBase(algoBaseStorage.getAlgoBaseFilePath());
    }

    @Override
    public Optional<ReadOnlyAlgoBase> readAlgoBase(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return algoBaseStorage.readAlgoBase(filePath);
    }

    @Override
    public void saveAlgoBase(ReadOnlyAlgoBase algoBase) throws IOException {
        saveAlgoBase(algoBase, algoBaseStorage.getAlgoBaseFilePath());
    }

    @Override
    public void saveAlgoBase(ReadOnlyAlgoBase algoBase, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        algoBaseStorage.saveAlgoBase(algoBase, filePath);
    }

}
