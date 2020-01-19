package seedu.mark.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.mark.commons.core.LogsCenter;
import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.ReadOnlyUserPrefs;
import seedu.mark.model.UserPrefs;

/**
 * Manages storage of Mark data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MarkStorage markStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(MarkStorage markStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.markStorage = markStorage;
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


    // ================ Mark methods ==============================

    @Override
    public Path getMarkFilePath() {
        return markStorage.getMarkFilePath();
    }

    @Override
    public Optional<ReadOnlyMark> readMark() throws DataConversionException, IOException {
        return readMark(markStorage.getMarkFilePath());
    }

    @Override
    public Optional<ReadOnlyMark> readMark(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return markStorage.readMark(filePath);
    }

    @Override
    public void saveMark(ReadOnlyMark mark) throws IOException {
        saveMark(mark, markStorage.getMarkFilePath());
    }

    @Override
    public void saveMark(ReadOnlyMark mark, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        markStorage.saveMark(mark, filePath);
    }

}
