package mams.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import mams.commons.core.LogsCenter;
import mams.commons.exceptions.DataConversionException;
import mams.commons.exceptions.IllegalValueException;
import mams.commons.util.FileUtil;
import mams.commons.util.JsonUtil;
import mams.logic.history.ReadOnlyCommandHistory;

/**
 * A class to access CommandHistory data stored as a json file on the hard disk.
 */
public class JsonCommandHistoryStorage implements CommandHistoryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCommandHistoryStorage.class);

    private Path filePath;

    public JsonCommandHistoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCommandHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCommandHistory> readCommandHistory() throws DataConversionException {
        return readCommandHistory(filePath);
    }

    /**
     * See {@link #readCommandHistory()}.
     *
     * @param filePath the location of the data file. must point to a valid file, and cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCommandHistory> readCommandHistory(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCommandHistory> jsonCommandHistory = JsonUtil.readJsonFile(
                filePath, JsonSerializableCommandHistory.class);
        if (jsonCommandHistory.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCommandHistory.get().toLogicType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCommandHistory(ReadOnlyCommandHistory commandHistory) throws IOException {
        saveCommandHistory(commandHistory, filePath);
    }

    /**
     * See {@link #saveCommandHistory(ReadOnlyCommandHistory)}.
     *
     * @param filePath the location of the data file. must point to a valid file, and cannot be null
     */
    public void saveCommandHistory(ReadOnlyCommandHistory commandHistory, Path filePath) throws IOException {
        requireNonNull(commandHistory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCommandHistory(commandHistory), filePath);
    }

}
