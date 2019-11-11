package seedu.address.storage.cap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.cap.ReadOnlyCapLog;


/**
 * Represents a storage for {@link seedu.address.model.cap.CapLog}.
 */
public interface CapStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCapLogFilePath();

    /**
     * Returns CapLog data as a {@link ReadOnlyCapLog}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCapLog> readCapLog() throws DataConversionException, IOException;

    /**
     * @see #getCapLogFilePath()
     */
    Optional<ReadOnlyCapLog> readCapLog(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCapLog} to the storage.
     * @param capLog cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCapLog(ReadOnlyCapLog capLog) throws IOException;

    /**
     * @see #saveCapLog(ReadOnlyCapLog, Path)
     */
    void saveCapLog(ReadOnlyCapLog capLog, Path filePath) throws IOException;

}
