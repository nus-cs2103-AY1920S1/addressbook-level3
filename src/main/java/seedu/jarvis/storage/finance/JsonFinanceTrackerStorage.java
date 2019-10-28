package seedu.jarvis.storage.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.commons.util.FileUtil;
import seedu.jarvis.commons.util.JsonUtil;
import seedu.jarvis.model.finance.FinanceTracker;

/**
 * A class to access {@code FinanceTracker} data stored as a text file on the hard disk.
 */
public class JsonFinanceTrackerStorage implements FinanceTrackerStorage {

    private Path filepath;

    public JsonFinanceTrackerStorage(Path filepath) {
        this.filepath = filepath;
    }

    /**
     * Gets the file path of the data file for {@code FinanceTracker}.
     *
     * @return File path of the data file for {@code FinanceTracker}.
     */
    @Override
    public Path getFinanceTrackerFilePath() {
        return filepath;
    }

    /**
     * Returns {@code FinanceTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code FinanceTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<FinanceTracker> readFinanceTracker() throws DataConversionException, IOException {
        return readFinanceTracker(filepath);
    }

    /**
     * Returns {@code FinanceTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code FinanceTracker} data.
     * @return {@code FinanceTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<FinanceTracker> readFinanceTracker(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);
        Optional<JsonSerializableFinanceTracker> jsonFinanceTracker = JsonUtil.readJsonFile(filePath,
                JsonSerializableFinanceTracker.class);
        if (jsonFinanceTracker.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFinanceTracker.get().toModelType());
        } catch (IllegalValueException ive) {
            throw new DataConversionException(ive);
        }
    }

    /**
     * Saves the given {@link FinanceTracker} to the storage.
     *
     * @param financeTracker {@code FinanceTracker} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveFinanceTracker(FinanceTracker financeTracker) throws IOException {
        saveFinanceTracker(financeTracker, filepath);
    }

    /**
     * Saves the given {@link FinanceTracker} to the storage.
     *
     * @param financeTracker {@code FinanceTracker} to be saved, which cannot be null.
     * @param filePath       {@code Path} to read {@code FinanceTracker} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveFinanceTracker(FinanceTracker financeTracker, Path filePath) throws IOException {
        requireAllNonNull(financeTracker, filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFinanceTracker(financeTracker), filePath);
    }
}
