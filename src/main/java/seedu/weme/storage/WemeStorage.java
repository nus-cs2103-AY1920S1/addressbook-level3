package seedu.weme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.Weme;

/**
 * Represents a storage for {@link Weme}.
 */
public interface WemeStorage {

    /**
     * Returns the folder path of the data folder.
     */
    Path getWemeFolderPath();

    /**
     * Returns Weme data as a {@link ReadOnlyWeme}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWeme> readWeme() throws DataConversionException, IOException;

    /**
     * @see #getWemeFolderPath()
     */
    Optional<ReadOnlyWeme> readWeme(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWeme} to the storage.
     * @param weme cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWeme(ReadOnlyWeme weme) throws IOException;

    /**
     * @see #saveWeme(ReadOnlyWeme)
     */
    void saveWeme(ReadOnlyWeme weme, Path filePath) throws IOException;

}
