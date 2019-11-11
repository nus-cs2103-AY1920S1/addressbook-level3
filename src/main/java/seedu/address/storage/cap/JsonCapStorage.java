package seedu.address.storage.cap;

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
import seedu.address.model.cap.ReadOnlyCapLog;


/**
 * A class to access CapLog data stored as a json file on the hard disk.
 */
public class JsonCapStorage implements CapStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCapStorage.class);

    private Path filePath;

    public JsonCapStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCapLogFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCapLog> readCapLog() throws DataConversionException {
        return readCapLog(filePath);
    }

    /**
     * Similar to {@link #readCapLog()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCapLog> readCapLog(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCapLog> jsonCapLog = JsonUtil.readJsonFile(
                filePath, JsonSerializableCapLog.class);
        if (!jsonCapLog.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCapLog.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCapLog(ReadOnlyCapLog capLog) throws IOException {
        saveCapLog(capLog, filePath);
    }

    /**
     * Similar to {@link #saveCapLog(ReadOnlyCapLog)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCapLog(ReadOnlyCapLog capLog, Path filePath) throws IOException {
        requireNonNull(capLog);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCapLog(capLog), filePath);
    }

}
