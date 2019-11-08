package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAthletick;
import seedu.address.model.ReadOnlyPerformance;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.TrainingManager;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Athletick data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AthletickStorage athletickStorage;
    private PerformanceStorage performanceStorage;
    private UserPrefsStorage userPrefsStorage;
    private TrainingManagerStorage trainingManagerStorage;


    public StorageManager(AthletickStorage athletickStorage, PerformanceStorage performanceStorage,
                          TrainingManagerStorage trainingManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.athletickStorage = athletickStorage;
        this.performanceStorage = performanceStorage;
        this.trainingManagerStorage = trainingManagerStorage;
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


    // ================ Athletick methods ==============================

    @Override
    public Path getAthletickFilePath() {
        return athletickStorage.getAthletickFilePath();
    }

    @Override
    public Optional<ReadOnlyAthletick> readAthletick() throws DataConversionException,
            IOException {
        return readAthletick(athletickStorage.getAthletickFilePath());
    }

    @Override
    public Optional<ReadOnlyAthletick> readAthletick(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return athletickStorage.readAthletick(filePath);
    }

    @Override
    public void saveAthletick(ReadOnlyAthletick athletick) throws IOException {
        saveAthletick(athletick, athletickStorage.getAthletickFilePath());
    }

    @Override
    public void saveAthletick(ReadOnlyAthletick athletick, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        athletickStorage.saveAthletick(athletick, filePath);
    }

    // ================ Performance methods ==============================

    @Override
    public Path getEventFilePath() {
        return performanceStorage.getEventFilePath();
    }

    @Override
    public Optional<ReadOnlyPerformance> readEvents() throws DataConversionException, IOException {
        return readEvents(performanceStorage.getEventFilePath());
    }

    @Override
    public Optional<ReadOnlyPerformance> readEvents(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return performanceStorage.readEvents(filePath);
    }

    @Override
    public void saveEvents(ReadOnlyPerformance events) throws IOException {
        saveEvents(events, performanceStorage.getEventFilePath());
    }

    @Override
    public void saveEvents(ReadOnlyPerformance events, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        performanceStorage.saveEvents(events, filePath);
    }

    // ================ Attendance methods ==============================

    @Override
    public Optional<TrainingManager> readTrainingManager() throws DataConversionException, IOException {
        return trainingManagerStorage.readTrainingManager();
    }

    @Override
    public Optional<TrainingManager> readTrainingManager(Path filePath) throws DataConversionException, IOException {
        return trainingManagerStorage.readTrainingManager(filePath);
    }

    @Override
    public void saveTrainingManager(TrainingManager trainingManager) throws IOException {
        trainingManagerStorage.saveTrainingManager(trainingManager);
    }

    @Override
    public void saveTrainingManager(TrainingManager trainingManager, Path filePath) throws IOException {
        trainingManagerStorage.saveTrainingManager(trainingManager, filePath);
    }
}
