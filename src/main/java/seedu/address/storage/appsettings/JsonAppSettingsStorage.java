package seedu.address.storage.appsettings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.appsettings.AppSettings;
/**
 * A class to access {@code WordBankStatistics} data stored as a json file on the hard disk.
 */
public class JsonAppSettingsStorage implements AppSettingsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAppSettingsStorage.class);

    private Path filePath;

    public JsonAppSettingsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getAppSettingsFilePath() {
        return filePath;
    }

    @Override
    public Optional<AppSettings> readAppSettings() throws DataConversionException, IOException {
        return readAppSettings(filePath);
    }

    /**
     * Similar to {@link #readAppSettings()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<AppSettings> readAppSettings(Path filePath) throws DataConversionException {
        try {
            requireNonNull(filePath);
            return JsonUtil.readJsonFile(filePath, AppSettings.class);
        } catch (InaccessibleObjectException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void saveAppSettings(AppSettings settings) throws IOException {
        saveAppSettings(settings, filePath);
    }

    /**
     * Similar to {@link #saveAppSettings(AppSettings)}.
     *
     * @param statistics statistics to be saved, cannot be null.
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveAppSettings(AppSettings statistics, Path filePath)
            throws IOException {
        requireAllNonNull(statistics, filePath);
        JsonUtil.saveJsonFile(statistics, filePath);
    }

}
