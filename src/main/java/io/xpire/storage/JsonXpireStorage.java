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
import io.xpire.model.ReadOnlyXpire;

/**
 * A class to access Xpire data stored as a json file on the hard disk.
 */
public class JsonXpireStorage implements XpireStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonXpireStorage.class);

    private Path filePath;

    public JsonXpireStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getXpireFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<ReadOnlyXpire> readXpire() throws DataConversionException {
        return readXpire(this.filePath);
    }

    /**
     * Similar to {@link #readXpire()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyXpire> readXpire(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableXpire> jsonTracker = JsonUtil.readJsonFile(
                filePath, JsonSerializableXpire.class);
        if (jsonTracker.isEmpty()) {
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
    public void saveXpire(ReadOnlyXpire xpire) throws IOException {
        saveXpire(xpire, this.filePath);
    }

    /**
     * Similar to {@link #saveXpire(ReadOnlyXpire)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveXpire(ReadOnlyXpire xpire, Path filePath) throws IOException {
        requireNonNull(xpire);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableXpire(xpire), filePath);
    }

}
