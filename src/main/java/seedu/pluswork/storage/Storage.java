package seedu.pluswork.storage;

import seedu.pluswork.commons.exceptions.DataConversionException;
import seedu.pluswork.model.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends ProjectDashboardStorage, UserPrefsStorage, UserSettingsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getProjectDashboardFilePath();

    @Override
    Optional<ReadOnlyProjectDashboard> readProjectDashBoard() throws DataConversionException, IOException;

    @Override
    void saveProjectDashboard(ReadOnlyProjectDashboard projectDashboard) throws IOException;

    @Override
    Path getUserSettingsFilePath();

    @Override
    Optional<UserSettings> readUserSettings() throws DataConversionException, IOException;

    @Override
    Optional<UserSettings> readUserSettings(Path filePath) throws DataConversionException;

    @Override
    void saveUserSettings(ReadOnlyUserSettings userSettings) throws IOException;

    @Override
    void saveUserSettings(ReadOnlyUserSettings userSettings, Path filePath) throws IOException;

}
