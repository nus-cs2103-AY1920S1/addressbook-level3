package io.xpire.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.commons.util.FileUtil;
import io.xpire.commons.util.JsonUtil;
import io.xpire.model.ReadOnlyExpiryDateTracker;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonExpiryDateTrackerStorage implements ExpiryDateTrackerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonExpiryDateTrackerStorage.class);

    private Path filePath;

    public JsonExpiryDateTrackerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getExpiryDateTrackerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyExpiryDateTracker> readExpiryDateTracker() throws DataConversionException {
        return readExpiryDateTracker(filePath);
    }

    /**
     * Similar to {@link #readExpiryDateTracker()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyExpiryDateTracker> readExpiryDateTracker(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableExpiryDateTracker> jsonTracker = JsonUtil.readJsonFile(
                filePath, JsonSerializableExpiryDateTracker.class);
        if (!jsonTracker.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTracker.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveExpiryDateTracker(ReadOnlyExpiryDateTracker expiryDateTracker) throws IOException {
        saveExpiryDateTracker(expiryDateTracker, filePath);
    }

    /**
     * Similar to {@link #saveExpiryDateTracker(ReadOnlyExpiryDateTracker)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveExpiryDateTracker(ReadOnlyExpiryDateTracker expiryDateTracker, Path filePath) throws IOException {
        requireNonNull(expiryDateTracker);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableExpiryDateTracker(expiryDateTracker), filePath);
    }

}
