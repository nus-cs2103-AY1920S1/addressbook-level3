package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyProjectDashboard;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyUserSettings;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserSettings;

/**
 * Manages storage of ProjectDashboard data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ProjectDashboardStorage projectDashboardStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ProjectDashboardStorage projectDashboardStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.projectDashboardStorage = projectDashboardStorage;
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

    // ================ Settings methods ==============================

    /**
     * Returns the file path of the UserSettings data file.
     */
    @Override
    public Path getUserSettingsFilePath() {
        return null;
    }

    /**
     * Returns UserSettings data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<UserSettings> readUserSettings() throws DataConversionException, IOException {
        return Optional.empty();
    }

    /**
     * @param filePath
     * @see #getUserSettingsFilePath().
     */
    @Override
    public Optional<UserSettings> readUserSettings(Path filePath) throws DataConversionException {
        return Optional.empty();
    }

    /**
     * Saves the given {@link ReadOnlyUserSettings} to the storage.
     *
     * @param userSettings cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    @Override
    public void saveUserSettings(ReadOnlyUserSettings userSettings) throws IOException {

    }

    @Override
    public void saveUserSettings(ReadOnlyUserSettings userSettings, Path filePath) throws IOException {

    }
}
