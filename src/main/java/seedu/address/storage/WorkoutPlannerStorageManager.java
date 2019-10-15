package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWorkoutPlanner;
import seedu.address.model.WorkoutPlannerUserPrefs;

/**
 * Manages storage of DukeCooks data in local storage.
 */
public class WorkoutPlannerStorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(WorkoutPlannerStorageManager.class);
    private WorkoutPlannerStorage workoutPlannerStorage;
    private UserPrefsStorage userPrefsStorage;


    public WorkoutPlannerStorageManager(WorkoutPlannerStorage workoutPlannerStorage,
                                        UserPrefsStorage userPrefsStorage) {
        super();
        this.workoutPlannerStorage = workoutPlannerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<WorkoutPlannerUserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ DukeCooks methods ==============================

    @Override
    public Path getDukeCooksFilePath() {
        return workoutPlannerStorage.getDukeCooksFilePath();
    }

    @Override
    public Optional<ReadOnlyWorkoutPlanner> readDukeCooks() throws DataConversionException, IOException {
        return readDukeCooks(workoutPlannerStorage.getDukeCooksFilePath());
    }

    @Override
    public Optional<ReadOnlyWorkoutPlanner> readDukeCooks(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return workoutPlannerStorage.readDukeCooks(filePath);
    }

    @Override
    public void saveDukeCooks(ReadOnlyWorkoutPlanner dukeCooks) throws IOException {
        saveDukeCooks(dukeCooks, workoutPlannerStorage.getDukeCooksFilePath());
    }

    @Override
    public void saveDukeCooks(ReadOnlyWorkoutPlanner dukeCooks, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        workoutPlannerStorage.saveDukeCooks(dukeCooks, filePath);
    }

}
