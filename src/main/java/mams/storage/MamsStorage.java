package mams.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import mams.commons.exceptions.DataConversionException;
import mams.model.Mams;
import mams.model.ReadOnlyMams;

/**
 * Represents a storage for {@link Mams}.
 */
public interface MamsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMamsFilePath();

    /**
     * Returns Mams data as a {@link ReadOnlyMams}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMams> readMams() throws DataConversionException, IOException;

    /**
     * @see #getMamsFilePath()
     */
    Optional<ReadOnlyMams> readMams(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMams} to the storage.
     * @param mams cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMams(ReadOnlyMams mams) throws IOException;

    /**
     * @see #saveMams(ReadOnlyMams)
     */
    void saveMams(ReadOnlyMams mams, Path filePath) throws IOException;

}
