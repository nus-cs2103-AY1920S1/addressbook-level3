package seedu.address.storage.cap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.cap.ReadOnlyModulo;


/**
 * Represents a storage for {@link seedu.address.model.cap.CapLog}.
 */
public interface CapStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCapLogFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyModulo}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyModulo> readCapLog() throws DataConversionException, IOException;

    /**
     * @see #getCapLogFilePath()
     */
    Optional<ReadOnlyModulo> readCapLog(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyModulo} to the storage.
     * @param capLog cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCapLog(ReadOnlyModulo capLog) throws IOException;

    /**
     * @see #saveCapLog(ReadOnlyModulo, Path) 
     */
    void saveCapLog(ReadOnlyModulo capLog, Path filePath) throws IOException;

}
