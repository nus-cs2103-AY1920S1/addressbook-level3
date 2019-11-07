package seedu.revision.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.revision.commons.exceptions.DataConversionException;
import seedu.revision.model.ReadOnlyHistory;

/**
 * Represents a storage for {@link seedu.revision.model.History}.
 */
public interface HistoryStorage {
    /**
     * Returns the file path of the History data file.
     */
    Path getHistoryFilePath();

    /**
     * Returns History data as a {@link ReadOnlyHistory}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyHistory> readHistory() throws DataConversionException, IOException;

    /**
     * @see #getHistoryFilePath()
     */
    Optional<ReadOnlyHistory> readHistory(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyHistory} to the storage.
     * @param history cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHistory(ReadOnlyHistory history) throws IOException;

    /**
     * @see #saveHistory(ReadOnlyHistory)
     */
    void saveHistory(ReadOnlyHistory history, Path filePath) throws IOException;

}
