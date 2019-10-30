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

/**
 * A class to access Weme data stored as a json file on the hard disk.
 */
public class JsonWemeStorage implements WemeStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWemeStorage.class);

    private Path filePath;

    public JsonWemeStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getWemeFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWeme> readWeme() throws DataConversionException {
        return readWeme(filePath);
    }

    /**
     * Similar to {@link #readWeme()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWeme> readWeme(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableWeme> jsonWeme = JsonUtil.readJsonFile(
                filePath, JsonSerializableWeme.class);
        if (!jsonWeme.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWeme.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWeme(ReadOnlyWeme weme) throws IOException {
        saveWeme(weme, filePath);
    }

    /**
     * Similar to {@link #saveWeme(ReadOnlyWeme)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveWeme(ReadOnlyWeme weme, Path filePath) throws IOException {
        requireNonNull(weme);
        requireNonNull(filePath);

        FileUtil.createFileIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWeme(weme), filePath);
    }

}
