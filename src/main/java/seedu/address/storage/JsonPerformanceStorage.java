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
import seedu.address.model.ReadOnlyPerformance;

/**
 * A class to access EventList data stored as a json file on the hard disk.
 */
public class JsonPerformanceStorage implements PerformanceStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPerformanceStorage.class);

    private Path filePath;

    public JsonPerformanceStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEventFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPerformance> readEvents() throws DataConversionException {
        return readEvents(filePath);
    }

    /**
     * Similar to {@link #readEvents()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyPerformance> readEvents(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonSerializablePerformance> jsonEventList = JsonUtil.readJsonFile(
                filePath, JsonSerializablePerformance.class);
        if (!jsonEventList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEventList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveEvents(ReadOnlyPerformance events) throws IOException {
        saveEvents(events, filePath);
    }

    @Override
    public void saveEvents(ReadOnlyPerformance events, Path filePath) throws IOException {
        requireNonNull(events);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePerformance(events), filePath);
    }
}
