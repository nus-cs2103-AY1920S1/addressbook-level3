package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAppData;

/**
 * A class to access AppData data stored as a json file on the hard disk.
 */
public class JsonAppDataStorage implements AppDataStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAppDataStorage.class);

    private Path filePath;

    public JsonAppDataStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAppDataFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAppData> readAppData() throws DataConversionException {
        return readAppData(filePath);
    }

    /**
     * Similar to {@link #readAppData()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAppData> readAppData(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAppData> jsonAppData = JsonUtil.readJsonFile(
                filePath, JsonSerializableAppData.class);
        if (!jsonAppData.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAppData.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAppData(ReadOnlyAppData appData) throws IOException {
        saveAppData(appData, filePath);
    }

    /**
     * Similar to {@link #saveAppData(ReadOnlyAppData)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAppData(ReadOnlyAppData appData, Path filePath) throws IOException {
        requireNonNull(appData);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAppData(appData), filePath);
    }

}
