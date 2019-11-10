package seedu.guilttrip.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.exceptions.DataConversionException;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;
import seedu.guilttrip.model.ReadOnlyUserPrefs;
import seedu.guilttrip.model.UserPrefs;

/**
 * Manages storage of GuiltTrip data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private GuiltTripStorage guiltTripStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(GuiltTripStorage guiltTripStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.guiltTripStorage = guiltTripStorage;
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


    // ================ GuiltTrip methods ==============================

    @Override
    public Path getGuiltTripFilePath() {
        return guiltTripStorage.getGuiltTripFilePath();
    }

    @Override
    public Optional<ReadOnlyGuiltTrip> readGuiltTrip() throws DataConversionException, IOException {
        return readGuiltTrip(guiltTripStorage.getGuiltTripFilePath());
    }

    @Override
    public Optional<ReadOnlyGuiltTrip> readGuiltTrip(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return guiltTripStorage.readGuiltTrip(filePath);
    }

    @Override
    public void saveGuiltTrip(ReadOnlyGuiltTrip addressBook) throws IOException {
        saveGuiltTrip(addressBook, guiltTripStorage.getGuiltTripFilePath());
    }

    @Override
    public void saveGuiltTrip(ReadOnlyGuiltTrip addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        guiltTripStorage.saveGuiltTrip(addressBook, filePath);
    }

}
