package seedu.weme.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.weme.commons.core.LogsCenter;
import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.commons.util.FileUtil;
import seedu.weme.commons.util.JsonUtil;
import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.UserPrefs;

/**
 * A class to access Weme data stored as a json file on the hard disk.
 */
public class JsonWemeStorage implements WemeStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWemeStorage.class);

    private Path folderPath;

    public JsonWemeStorage(Path folderPath) {
        this.folderPath = folderPath;
    }

    public Path getWemeFolderPath() {
        return folderPath;
    }

    @Override
    public Optional<ReadOnlyWeme> readWeme() throws DataConversionException {
        return readWeme(folderPath);
    }

    /**
     * Similar to {@link #readWeme()}.
     *
     * @param folderPath location of the data folder. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWeme> readWeme(Path folderPath) throws DataConversionException {
        requireNonNull(folderPath);

        Path dataFilePath = folderPath.resolve(UserPrefs.DATA_FILE_NAME);

        Optional<JsonSerializableWeme> jsonWeme = JsonUtil.readJsonFile(
                dataFilePath, JsonSerializableWeme.class);
        if (!jsonWeme.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWeme.get().toModelType(folderPath));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + dataFilePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWeme(ReadOnlyWeme weme) throws IOException {
        saveWeme(weme, folderPath);
    }

    /**
     * Similar to {@link #saveWeme(ReadOnlyWeme)}.
     *
     * @param folderPath location of the data folder. Cannot be null.
     */
    public void saveWeme(ReadOnlyWeme weme, Path folderPath) throws IOException {
        requireNonNull(weme);
        requireNonNull(folderPath);

        Path dataFilePath = folderPath.resolve(UserPrefs.DATA_FILE_NAME);

        FileUtil.createFileIfMissing(dataFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableWeme(weme, folderPath), dataFilePath);
    }

}
