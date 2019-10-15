package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.profile.ReadOnlyDukeCooks;
import seedu.address.profile.ReadOnlyHealthRecords;
import seedu.address.profile.ReadOnlyUserPrefs;
import seedu.address.profile.UserPrefs;

/**
 * Manages storage of DukeCooks data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private DukeCooksStorage dukeCooksStorage;
    private HealthRecordsStorage healthRecordsStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(DukeCooksStorage dukeCooksStorage, HealthRecordsStorage healthRecordsStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.dukeCooksStorage = dukeCooksStorage;
        this.healthRecordsStorage = healthRecordsStorage;
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


    // ================ DukeCooks methods ==============================

    @Override
    public Path getDukeCooksFilePath() {
        return dukeCooksStorage.getDukeCooksFilePath();
    }

    @Override
    public Optional<ReadOnlyDukeCooks> readDukeCooks() throws DataConversionException, IOException {
        return readDukeCooks(dukeCooksStorage.getDukeCooksFilePath());
    }

    @Override
    public Optional<ReadOnlyDukeCooks> readDukeCooks(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return dukeCooksStorage.readDukeCooks(filePath);
    }

    @Override
    public void saveDukeCooks(ReadOnlyDukeCooks dukeCooks) throws IOException {
        saveDukeCooks(dukeCooks, dukeCooksStorage.getDukeCooksFilePath());
    }

    @Override
    public void saveDukeCooks(ReadOnlyDukeCooks dukeCooks, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        dukeCooksStorage.saveDukeCooks(dukeCooks, filePath);
    }

    // ================ Health Records methods ==============================

    @Override
    public Path getHealthRecordsFilePath() {
        return healthRecordsStorage.getHealthRecordsFilePath();
    }

    @Override
    public Optional<ReadOnlyHealthRecords> readHealthRecords() throws DataConversionException, IOException {
        return readHealthRecords(healthRecordsStorage.getHealthRecordsFilePath());
    }

    @Override
    public Optional<ReadOnlyHealthRecords> readHealthRecords(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return healthRecordsStorage.readHealthRecords(filePath);
    }

    @Override
    public void saveHealthRecords(ReadOnlyHealthRecords healthRecords) throws IOException {
        saveHealthRecords(healthRecords, healthRecordsStorage.getHealthRecordsFilePath());
    }

    @Override
    public void saveHealthRecords(ReadOnlyHealthRecords healthRecords, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        healthRecordsStorage.saveHealthRecords(healthRecords, filePath);
    }
}
