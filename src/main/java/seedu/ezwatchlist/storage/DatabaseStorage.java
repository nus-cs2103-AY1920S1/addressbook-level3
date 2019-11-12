package seedu.ezwatchlist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.ezwatchlist.commons.exceptions.DataConversionException;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.WatchList;

/**
 * Represents a database storage for {@link WatchList}.
 */
public interface DatabaseStorage {

    /**
     * Returns the file path of the database file.
     */
    Path getDatabaseFilePath();

    /**
     * Returns database as a {@link ReadOnlyWatchList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWatchList> readDatabase() throws DataConversionException, IOException;

    /**
     * @see #getDatabaseFilePath()
     */
    Optional<ReadOnlyWatchList> readDatabase(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWatchList} to the storage.
     * @param database cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDatabase(ReadOnlyWatchList database) throws IOException;

    /**
     * @see #saveDatabase(ReadOnlyWatchList)
     */
    void saveDatabase(ReadOnlyWatchList database, Path filePath) throws IOException;

}
