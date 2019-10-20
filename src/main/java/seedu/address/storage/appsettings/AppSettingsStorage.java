package seedu.address.storage.appsettings;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.appsettings.ReadOnlyAppSettings;
import seedu.address.statistics.WordBankStatistics;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link WordBankStatistics}.
 */
public interface AppSettingsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAppSettingsFilePath();

    /**
     * Returns app settings data as a {@link AppSettings}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<AppSettings> readAppSettings() throws DataConversionException, IOException;

    /**
     * @see #getAppSettingsFilePath()
     */
    Optional<AppSettings> readAppSettings(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.statistics.GameStatistics} to the storage.
     * @param settings The settings to be saved, cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAppSettings(AppSettings settings) throws IOException;

    void saveAppSettings(AppSettings settings, Path filePath) throws IOException;
}
