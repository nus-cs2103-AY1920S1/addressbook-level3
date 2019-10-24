package seedu.jarvis.storage.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.commons.util.FileUtil;
import seedu.jarvis.commons.util.JsonUtil;
import seedu.jarvis.model.cca.CcaTracker;

/**
 * A class to access {@code CcaTracker} data stored as a text file on the hard disk.
 */
public class JsonCcaTrackerStorage implements CcaTrackerStorage {

    private Path filepath;

    public JsonCcaTrackerStorage(Path filepath) {
        this.filepath = filepath;
    }


    /**
     * Gets the file path of the data file for {@code CcaTracker}.
     *
     * @return File path of the data file for {@code CcaTracker}.
     */
    @Override
    public Path getCcaTrackerFilePath() {
        return filepath;
    }

    /**
     * Returns {@code CcaTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code CcaTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<CcaTracker> readCcaTracker() throws DataConversionException, IOException {
        return readCcaTracker(filepath);
    }

    /**
     * Returns {@code CcaTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code CcaTracker} data.
     * @return {@code CcaTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<CcaTracker> readCcaTracker(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);
        Optional<JsonSerializableCcaTracker> jsonCcaTracker = JsonUtil.readJsonFile(filePath,
                JsonSerializableCcaTracker.class);
        if (jsonCcaTracker.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCcaTracker.get().toModelType());
        } catch (IllegalValueException ive) {
            throw new DataConversionException(ive);
        }
    }

    /**
     * Saves the given {@link CcaTracker} to the storage.
     *
     * @param ccaTracker {@code CcaTracker} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveCcaTracker(CcaTracker ccaTracker) throws IOException {
        saveCcaTracker(ccaTracker, filepath);
    }

    /**
     * Saves the given {@link CcaTracker} to the storage.
     *
     * @param ccaTracker {@code CcaTracker} to be saved, which cannot be null.
     * @param filePath   {@code Path} to read {@code CcaTracker} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveCcaTracker(CcaTracker ccaTracker, Path filePath) throws IOException {
        requireAllNonNull(ccaTracker, filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCcaTracker(ccaTracker), filePath);
    }
}
