package seedu.exercise.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.commons.util.FileUtil;
import seedu.exercise.commons.util.JsonUtil;
import seedu.exercise.model.exercise.PropertyManager;

/**
 * A class to access PropertyManager data stored as a json file in the hard disk.
 */
public class JsonPropertyManagerStorage implements PropertyManagerStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonPropertyManagerStorage.class);

    private Path filePath;

    public JsonPropertyManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPropertyManagerFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<PropertyManager> readPropertyManager() throws DataConversionException {
        return readPropertyManager(filePath);
    }

    /**
     * Similar to {@link #readPropertyManager()}
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format
     */
    public Optional<PropertyManager> readPropertyManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePropertyManager> jsonPropertyManager =
            JsonUtil.readJsonFile(filePath, JsonSerializablePropertyManager.class);
        if (jsonPropertyManager.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonPropertyManager.get().toModelManager());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    public void savePropertyManager(PropertyManager propertyManager) throws IOException {
        savePropertyManager(propertyManager, filePath);
    }

    /**
     * Similar to {@link #savePropertyManager(PropertyManager)}
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePropertyManager(PropertyManager propertyManager, Path filePath) throws IOException {
        requireNonNull(propertyManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePropertyManager(propertyManager), filePath);
    }
}
