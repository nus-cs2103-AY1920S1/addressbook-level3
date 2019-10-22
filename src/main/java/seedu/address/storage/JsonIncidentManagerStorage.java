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
import seedu.address.model.ReadOnlyIncidentManager;

/**
 * A class to access IncidentManager data stored as a json file on the hard disk.
 */
public class JsonIncidentManagerStorage implements IncidentManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonIncidentManagerStorage.class);

    private Path filePath;

    public JsonIncidentManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getIncidentManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyIncidentManager> readIncidentManager() throws DataConversionException {
        return readIncidentManager(filePath);
    }

    /**
     * Similar to {@link #readIncidentManager()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyIncidentManager> readIncidentManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableIncidentManager> jsonIncidentManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableIncidentManager.class);
        if (!jsonIncidentManager.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonIncidentManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveIncidentManager(ReadOnlyIncidentManager incidentManager) throws IOException {
        saveIncidentManager(incidentManager, filePath);
    }

    /**
     * Similar to {@link #saveIncidentManager(ReadOnlyIncidentManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveIncidentManager(ReadOnlyIncidentManager incidentManager, Path filePath) throws IOException {
        requireNonNull(incidentManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableIncidentManager(incidentManager), filePath);
    }

}
