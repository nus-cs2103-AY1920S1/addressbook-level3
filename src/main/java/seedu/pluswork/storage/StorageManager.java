package seedu.pluswork.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.pluswork.commons.core.LogsCenter;
import seedu.pluswork.commons.exceptions.DataConversionException;
import seedu.pluswork.model.ReadOnlyProjectDashboard;
import seedu.pluswork.model.ReadOnlyUserPrefs;
import seedu.pluswork.model.ReadOnlyUserSettings;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;

/**
 * Manages storage of ProjectDashboard data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ProjectDashboardStorage projectDashboardStorage;
    private UserPrefsStorage userPrefsStorage;
    private UserSettingsStorage userSettingsStorage;

    public StorageManager(ProjectDashboardStorage projectDashboardStorage, UserPrefsStorage userPrefsStorage,
                          UserSettingsStorage userSettingsStorage) {
        super();
        this.projectDashboardStorage = projectDashboardStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userSettingsStorage = userSettingsStorage;
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


    // ================ ProjectDashboard methods ==============================

    @Override
    public Path getProjectDashboardFilePath() {
        return projectDashboardStorage.getProjectDashboardFilePath();
    }

    @Override
    public Optional<ReadOnlyProjectDashboard> readProjectDashBoard() throws DataConversionException, IOException {
        return readProjectDashBoard(projectDashboardStorage.getProjectDashboardFilePath());
    }

    @Override
    public Optional<ReadOnlyProjectDashboard> readProjectDashBoard(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return projectDashboardStorage.readProjectDashBoard(filePath);
    }

    @Override
    public void saveProjectDashboard(ReadOnlyProjectDashboard projectDashboard) throws IOException {
        saveProjectDashboard(projectDashboard, projectDashboardStorage.getProjectDashboardFilePath());
    }

    @Override
    public void saveProjectDashboard(ReadOnlyProjectDashboard projectDashboard, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        projectDashboardStorage.saveProjectDashboard(projectDashboard, filePath);
    }

    // ================ UserSettings methods ==============================

    @Override
    public Path getUserSettingsFilePath() {
        return userSettingsStorage.getUserSettingsFilePath();
    }

    @Override
    public Optional<UserSettings> readUserSettings() throws DataConversionException, IOException {
        return readUserSettings(userSettingsStorage.getUserSettingsFilePath());
    }

    @Override
    public Optional<UserSettings> readUserSettings(Path filePath) throws DataConversionException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userSettingsStorage.readUserSettings(filePath);
    }

    @Override
    public void saveUserSettings(ReadOnlyUserSettings userSettings) throws IOException {
        saveUserSettings(userSettings, userSettingsStorage.getUserSettingsFilePath());
    }

    @Override
    public void saveUserSettings(ReadOnlyUserSettings userSettings, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userSettingsStorage.saveUserSettings(userSettings, filePath);
    }
}
