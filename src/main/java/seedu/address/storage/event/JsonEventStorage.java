package seedu.address.storage.event;

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
import seedu.address.model.event.ReadOnlyEvents;

/**
 * A class to access Events data stored as a json file on the hard disk.
 */
public class JsonEventStorage implements EventStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEventStorage.class);

    private Path filePath;

    public JsonEventStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEventRecordFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEvents> readEvents() throws DataConversionException {
        return readEvents(filePath);
    }

    /**
     * Similar to {@link #readEvents()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEvents> readEvents(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableEvents> jsonEvents = JsonUtil.readJsonFile(filePath, JsonSerializableEvents.class);
        if (!jsonEvents.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEvents.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveEvents(ReadOnlyEvents events) throws IOException {
        saveEvents(events, filePath);
    }

    /**
     * Similar to {@link #saveEvents(ReadOnlyEvents)}.
     *
     * @param filePath location of the data.
     */
    public void saveEvents(ReadOnlyEvents events, Path filePath) throws IOException {
        requireNonNull(events);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEvents(events), filePath);
    }

}
