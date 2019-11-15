package seedu.revision.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.revision.commons.core.LogsCenter;
import seedu.revision.commons.exceptions.DataConversionException;
import seedu.revision.commons.exceptions.IllegalValueException;
import seedu.revision.commons.util.FileUtil;
import seedu.revision.commons.util.JsonUtil;
import seedu.revision.model.ReadOnlyHistory;

/**
 * A class to access History data stored as a json file on the hard disk.
 */
public class JsonHistoryStorage implements HistoryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonHistoryStorage.class);

    private Path filePath;

    public JsonHistoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyHistory> readHistory() throws DataConversionException {
        return readHistory(filePath);
    }

    /**
     * Similar to {@link #readHistory()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyHistory> readHistory(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableHistory> jsonHistory = JsonUtil.readJsonFile(
                filePath, JsonSerializableHistory.class);
        if (!jsonHistory.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonHistory.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveHistory(ReadOnlyHistory history) throws IOException {
        saveHistory(history, filePath);
    }

    /**
     * Similar to {@link #saveHistory(ReadOnlyHistory)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveHistory(ReadOnlyHistory history, Path filePath) throws IOException {
        requireNonNull(history);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHistory(history), filePath);
    }

}
