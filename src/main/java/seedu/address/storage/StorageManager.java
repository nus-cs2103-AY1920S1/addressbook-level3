package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyIncidentManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of IncidentManager data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private IncidentManagerStorage incidentManagerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(IncidentManagerStorage incidentManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.incidentManagerStorage = incidentManagerStorage;
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


    // ================ IncidentManager methods ==============================

    @Override
    public Path getIncidentManagerFilePath() {
        return incidentManagerStorage.getIncidentManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyIncidentManager> readIncidentManager() throws DataConversionException, IOException {
        return readIncidentManager(incidentManagerStorage.getIncidentManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyIncidentManager> readIncidentManager(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return incidentManagerStorage.readIncidentManager(filePath);
    }

    @Override
    public void saveIncidentManager(ReadOnlyIncidentManager incidentManager) throws IOException {
        assert(incidentManager != null);
        assert(incidentManagerStorage != null);
        assert(incidentManagerStorage.getIncidentManagerFilePath() != null);
        saveIncidentManager(incidentManager, incidentManagerStorage.getIncidentManagerFilePath());
    }

    @Override
    public void saveIncidentManager(ReadOnlyIncidentManager incidentManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        incidentManagerStorage.saveIncidentManager(incidentManager, filePath);
    }

}
