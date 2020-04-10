package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAthletick;

/**
 * Represents a storage for {@link seedu.address.model.Athletick}.
 */
public interface AthletickStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAthletickFilePath();

    /**
     * Returns Athletick data as a {@link ReadOnlyAthletick}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAthletick> readAthletick() throws DataConversionException, IOException;

    /**
     * @see #getAthletickFilePath()
     */
    Optional<ReadOnlyAthletick> readAthletick(Path filePath) throws DataConversionException,
            IOException;

    /**
     * Saves the given {@link ReadOnlyAthletick} to the storage.
     * @param athletick cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAthletick(ReadOnlyAthletick athletick) throws IOException;

    /**
     * @see #saveAthletick(ReadOnlyAthletick)
     */
    void saveAthletick(ReadOnlyAthletick athletick, Path filePath) throws IOException;

}
