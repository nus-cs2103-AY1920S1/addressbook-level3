package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyPlanner;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Planner data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PlannerStorage plannerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(PlannerStorage plannerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.plannerStorage = plannerStorage;
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


    // ================ Planner methods ==============================

    @Override
    public Path getPlannerFilePath() {
        return plannerStorage.getPlannerFilePath();
    }

    @Override
    public Optional<ReadOnlyPlanner> readPlanner() throws DataConversionException, IOException {
        return readPlanner(plannerStorage.getPlannerFilePath());
    }

    @Override
    public Optional<ReadOnlyPlanner> readPlanner(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return plannerStorage.readPlanner(filePath);
    }

    @Override
    public void savePlanner(ReadOnlyPlanner planner) throws IOException {
        savePlanner(planner, plannerStorage.getPlannerFilePath());
    }

    @Override
    public void savePlanner(ReadOnlyPlanner planner, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        plannerStorage.savePlanner(planner, filePath);
    }

}
