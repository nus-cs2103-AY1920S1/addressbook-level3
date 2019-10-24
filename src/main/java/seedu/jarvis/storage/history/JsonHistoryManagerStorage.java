package seedu.jarvis.storage.history;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.commons.util.FileUtil;
import seedu.jarvis.commons.util.JsonUtil;
import seedu.jarvis.model.history.HistoryManager;


/**
 * A class to access {@code HistoryManager} data stored as a text file on the hard disk.
 */
public class JsonHistoryManagerStorage implements HistoryManagerStorage {

    private Path filepath;

    public JsonHistoryManagerStorage(Path filepath) {
        this.filepath = filepath;
    }

    /**
     * Gets the file path of the data file for {@code HistoryManager}.
     *
     * @return File path of the data file for {@code HistoryManager}.
     */
    public Path getHistoryManagerFilePath() {
        return filepath;
    }

    /**
     * Returns {@code HistoryManager} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code HistoryManager} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<HistoryManager> readHistoryManager() throws DataConversionException, IOException {
        return readHistoryManager(filepath);
    }

    /**
     * Returns {@code HistoryManager} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code HistoryManager} data.
     * @return {@code HistoryManager} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<HistoryManager> readHistoryManager(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);
        Optional<JsonSerializableHistoryManager> jsonHistoryManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableHistoryManager.class);
        if (jsonHistoryManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonHistoryManager.get().toModelType());
        } catch (IllegalValueException ive) {
            throw new DataConversionException(ive);
        }
    }

    /**
     * Saves the given {@link HistoryManager} to the storage.
     *
     * @param historyManager {@code HistoryManager} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveHistoryManager(HistoryManager historyManager) throws IOException {
        saveHistoryManager(historyManager, filepath);
    }

    /**
     * Saves the given {@link HistoryManager} to the storage.
     *
     * @param historyManager {@code HistoryManager} to be saved, which cannot be null.
     * @param filePath       {@code Path} to read {@code HistoryManager} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveHistoryManager(HistoryManager historyManager, Path filePath) throws IOException {
        requireAllNonNull(historyManager, filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHistoryManager(historyManager), filePath);
    }

}
