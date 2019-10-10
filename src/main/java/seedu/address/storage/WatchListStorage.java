package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWatchList;

/**
 * Represents a storage for {@link seedu.address.model.WatchList}.
 */
public interface WatchListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWatchListFilePath();

    /**
     * Returns WatchList data as a {@link ReadOnlyWatchList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWatchList> readWatchList() throws DataConversionException, IOException;

    /**
     * @see #getWatchListFilePath()
     */
    Optional<ReadOnlyWatchList> readWatchList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWatchList} to the storage.
     * @param watchList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWatchList(ReadOnlyWatchList watchList) throws IOException;

    /**
     * @see #saveWatchList(ReadOnlyWatchList)
     */
    void saveWatchList(ReadOnlyWatchList watchList, Path filePath) throws IOException;

}
