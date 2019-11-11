package seedu.jarvis.storage.history;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.history.HistoryManager;

/**
 * Represents a storage for {@link HistoryManager}.
 */
public interface HistoryManagerStorage {

    /**
     * Gets the file path of the data file for {@code HistoryManager}.
     *
     * @return File path of the data file for {@code HistoryManager}.
     */
    Path getHistoryManagerFilePath();

    /**
     * Returns {@code HistoryManager} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code HistoryManager} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<HistoryManager> readHistoryManager() throws DataConversionException, IOException;

    /**
     * Returns {@code HistoryManager} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code HistoryManager} data.
     * @return {@code HistoryManager} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<HistoryManager> readHistoryManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link HistoryManager} to the storage.
     *
     * @param historyManager {@code HistoryManager} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    void saveHistoryManager(HistoryManager historyManager) throws IOException;

    /**
     * Saves the given {@link HistoryManager} to the storage.
     *
     * @param historyManager {@code HistoryManager} to be saved, which cannot be null.
     * @param filePath {@code Path} to read {@code HistoryManager} data.
     * @throws IOException If there was any problem writing to the file.
     */
    void saveHistoryManager(HistoryManager historyManager, Path filePath) throws IOException;

}
