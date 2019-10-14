package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.profile.DukeCooks;
import seedu.address.profile.ReadOnlyDukeCooks;

/**
 * Represents a storage for {@link DukeCooks}.
 */
public interface DukeCooksStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDukeCooksFilePath();

    /**
     * Returns DukeCooks data as a {@link ReadOnlyDukeCooks}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDukeCooks> readDukeCooks() throws DataConversionException, IOException;

    /**
     * @see #getDukeCooksFilePath()
     */
    Optional<ReadOnlyDukeCooks> readDukeCooks(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDukeCooks} to the storage.
     * @param dukeCooks cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDukeCooks(ReadOnlyDukeCooks dukeCooks) throws IOException;

    /**
     * @see #saveDukeCooks(ReadOnlyDukeCooks)
     */
    void saveDukeCooks(ReadOnlyDukeCooks dukeCooks, Path filePath) throws IOException;

}
