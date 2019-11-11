package mams.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import mams.commons.exceptions.DataConversionException;
import mams.logic.history.ReadOnlyCommandHistory;

/**
 * Represents a storage for {@link mams.logic.history.CommandHistory}.
 */
public interface CommandHistoryStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getCommandHistoryFilePath();

    /**
     * Returns CommandHistory data as a {@link ReadOnlyCommandHistory}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCommandHistory> readCommandHistory() throws DataConversionException, IOException;

    /**
     * @see #getCommandHistoryFilePath()
     */
    Optional<ReadOnlyCommandHistory> readCommandHistory(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCommandHistory} to storage.
     * @param commandHistory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCommandHistory(ReadOnlyCommandHistory commandHistory) throws IOException;

    /**
     * @see #saveCommandHistory(ReadOnlyCommandHistory)
     */
    void saveCommandHistory(ReadOnlyCommandHistory commandHistory, Path filePath) throws IOException;
}
